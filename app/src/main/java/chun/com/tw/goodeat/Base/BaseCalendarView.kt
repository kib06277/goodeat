package chun.com.tw.goodeat.Base

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.marginTop
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import chun.com.tw.goodeat.Adapter.CalendarAdapter
import chun.com.tw.goodeat.Bean.ClickableType
import chun.com.tw.goodeat.Bean.DateInfo
import chun.com.tw.goodeat.Bean.ViewAttrs
import chun.com.tw.goodeat.R
import chun.com.tw.goodeat.Util.Util
import chun.com.tw.goodeat.View.BaseHeaderView
import chun.com.tw.goodeat.View.WeekTitleView
import java.util.*

/**
 * 日曆控件基礎
 */
abstract class BaseCalendarView(
    context: Context,
    attrs: AttributeSet
) : LinearLayout(context, attrs) {

    // 當前選中的日期
    protected var selectedDate = Calendar.getInstance()
    // 最小日期
    private val minDate = Calendar.getInstance()
    // 最大日期
    private val maxDate = Calendar.getInstance()
    // 臨時日期
    private val tempCalendar: Calendar = Calendar.getInstance()
    // 適配器
    private lateinit var calendarAdapter: CalendarAdapter

    private lateinit var weekTitleView: WeekTitleView

    protected lateinit var viewPager: ViewPager
    // 日曆頭部，包括上下月按钮，當前年月標題
    protected lateinit var headerView: BaseHeaderView

    // 控件属性
    protected lateinit var viewAttrs: ViewAttrs

    // 上下、左右間距
    private var padding: Int = Util.dp2px(context, 10f).toInt()

    // 日曆點擊類型
    private var clickableType = ClickableType.NORMAL

    // 可點擊日期
    var clickableDateList: List<DateInfo>? = null
        set(value) {
            checkClickableDateList()
            field = value
            value?.let {clickableType = ClickableType.CLICKABLE}
        }

    // 不可點擊日期
    var unClickableDateList: List<DateInfo>? = null
        set(value) {
            checkClickableDateList()
            field = value
            value?.let {clickableType = ClickableType.UN_CLICKABLE}
        }

    private var onPageChangeListener: OnPageChangeListener? = null

    private var pageChangeListener = object : OnPageChangeListener {
        override fun onPageScrolled(position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int) {
            onPageChangeListener?.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageScrollStateChanged(state: Int) {
            onPageChangeListener?.onPageScrollStateChanged(state)
        }

        override fun onPageSelected(position: Int) {
            onPageChangeListener?.onPageSelected(position)
            viewPager.findViewWithTag<BaseMonthView>(Util.getMonthViewTag(position))?.let {
                val date = DateInfo().toDate(it.monthDate)
                headerView.updateTitle(date.year, date.month)
            }
            // 是否有上頁
            if (position == 0) {
                headerView.handlePrevNext(hasPrev = false, hasNext = true)
            }
            // 是否有下頁
            if (position == calendarAdapter.monthCount - 1) {
                headerView.handlePrevNext(hasPrev = true, hasNext = false)
            }
            if (position != 0 && position != calendarAdapter.monthCount - 1) {
                headerView.handlePrevNext(hasPrev = true, hasNext = true)
            }
        }
    }

    private var headerViewListener = object : BaseHeaderView.HeaderViewListener {
        override fun prevMonth() {
            viewPager.setCurrentItem(viewPager.currentItem - 1, true)
        }

        override fun nextMonth() {
            viewPager.setCurrentItem(viewPager.currentItem + 1, true)
        }
    }

    init {
        // 設置垂直方向顯示控件
        orientation = VERTICAL
        initViewAttrs(attrs)
        initViewPager()
        initHeaderView(viewAttrs.headerView)
        initWeekView()
        initDate()
        addView(viewPager)
    }

    /**
     * 初始化控件属性
     */
    protected open fun initViewAttrs(attrs: AttributeSet) {
        viewAttrs = Util.createViewAttrs(context, attrs)
    }

    /**
     * 初始化 view pager
     */
    private fun initViewPager() {
        viewPager = ViewPager(context)
        viewPager.setBackgroundColor(Color.TRANSPARENT)
        calendarAdapter = CalendarAdapter(minDate) { position: Int, monthDate: Calendar ->
            val monthView = createMonthView(position, monthDate, viewAttrs)
            monthView.tag = Util.getMonthViewTag(position)
            monthView.minDate = minDate
            monthView.maxDate = maxDate
            monthView.unClickableDateList = unClickableDateList
            monthView.clickableDateList = clickableDateList
            monthView.clickableType = clickableType
            monthView.padding = padding
            monthView
        }
        viewPager.adapter = calendarAdapter
        // WARNING 動態添加 view pager，需要設置 id， 否則方向旋轉時會出現以下錯誤
        // This usually happens when two views of different type have the same id in the same hierarchy.
        // This view's id is id/calendar_view. Make sure other views do not use the same id
        viewPager.id = R.id.view_pager
        viewPager.addOnPageChangeListener(pageChangeListener)
    }

    /**
     * 初始化標頭
     */
    private fun initHeaderView(headerViewName: String?) {
        headerView = Util.createHeaderView(context, headerViewName)
        headerView.listener = headerViewListener
        addView(headerView, 0)
    }

    /**
     * 初始化周控件
     */
    private fun initWeekView() {
        weekTitleView = WeekTitleView(context, viewAttrs)
        weekTitleView.padding = padding

        addView(weekTitleView)
    }

    /**
     * 初始化默認日期
     */
    private fun initDate() {
        // 默認最小日期 1970-1-1
        minDate.set(Calendar.YEAR, 1970)
        minDate.set(Calendar.MONTH, 0)
        minDate.set(Calendar.DAY_OF_MONTH, 1)
        setDateRange(minDate.timeInMillis, maxDate.timeInMillis, selectedDate.timeInMillis)
    }

    /**
     * 設置日期範圍
     * @param startTimeInMillis 開始日期，單位：毫秒
     * @param endTimeInMillis 结束日期，單位：毫秒
     * @param selectedTimeInMillis 當前日期，單位：毫秒
     */
    @JvmOverloads
    fun setDateRange(startTimeInMillis: Long = 0, endTimeInMillis: Long = 0, selectedTimeInMillis: Long = 0) {
        if (startTimeInMillis > endTimeInMillis) {
            throw IllegalArgumentException("startTimeInMillis【${startTimeInMillis}】不能大于endTimeInMillis【${endTimeInMillis}】")
        }
        if (startTimeInMillis != 0L) {
            minDate.timeInMillis = startTimeInMillis
        }
        if (endTimeInMillis != 0L) {
            maxDate.timeInMillis = endTimeInMillis
        }
        if (selectedTimeInMillis != 0L) {
            selectedDate.timeInMillis = selectedTimeInMillis
        }
        val currentDate = DateInfo().toDate(selectedDate)
        headerView.updateTitle(currentDate.year, currentDate.month)
        onRangeChanged()
    }

    /**
     * 日期範圍改變
     */
    private fun onRangeChanged() {
        calendarAdapter.setRange(minDate, maxDate)

        // Changing the min/max date changes the selection position since we
        // don't really have stable IDs. Jumps immediately to the new position.
        setDate(selectedDate.timeInMillis)
    }

    private fun setDate(timeInMillis: Long) {
        var timeInMillis = timeInMillis
        var dateClamped = false
        // Clamp the target day in milliseconds to the min or max if outside the range.
        if (timeInMillis < minDate.timeInMillis) {
            timeInMillis = minDate.timeInMillis
            dateClamped = true
        } else if (timeInMillis > maxDate.timeInMillis) {
            timeInMillis = maxDate.timeInMillis
            dateClamped = true
        }
        getTempCalendarForTime(timeInMillis)
        if (dateClamped) {
            selectedDate.timeInMillis = timeInMillis
        }
        val position: Int = getPositionFromDay(timeInMillis)
        if (position != viewPager.currentItem) {
            viewPager.setCurrentItem(position, false)
        }
    }

    /**
     * 獲取指定日期所在的索引號
     */
    private fun getPositionFromDay(timeInMillis: Long): Int {
        val diffMonthMax = getDiffMonths(minDate, maxDate)
        val diffMonth = getDiffMonths(minDate, getTempCalendarForTime(timeInMillis))
        return constrain(diffMonth, 0, diffMonthMax)
    }

    private fun constrain(amount: Int, low: Int, high: Int): Int {
        return if (amount < low) low else if (amount > high) high else amount
    }

    private fun getTempCalendarForTime(timeInMillis: Long): Calendar {
        tempCalendar.timeInMillis = timeInMillis
        return tempCalendar
    }

    private fun getDiffMonths(start: Calendar, end: Calendar): Int {
        val diffYears = end[Calendar.YEAR] - start[Calendar.YEAR]
        return end[Calendar.MONTH] - start[Calendar.MONTH] + 12 * diffYears
    }

    /**
     * 更新選中狀態
     */
    protected fun updateDateSelected(dateInfo: DateInfo, changeMonth: Boolean, monthPosition: Int) {
        if (changeMonth) {
            viewPager.setCurrentItem(monthPosition, true)
        }
        selectedDate = dateInfo.toCalendar()
        // 更新選中狀態
        updateSelected(monthPosition, dateInfo)
    }

    /**
     * 更新選中狀態
     */
    protected open fun updateSelected(monthPosition: Int, date: DateInfo) {
        val childCount = viewPager.childCount
        val viewTag = Util.getMonthViewTag(monthPosition)
        for (index in 0 until childCount) {
            var monthView = viewPager.getChildAt(index) as BaseMonthView
            monthView.updateByDateSelected(monthView.tag == viewTag, date)
            monthView.invalidate()
        }
    }

    private fun checkClickableDateList() {
        if (clickableDateList != null && unClickableDateList != null) {
            throw IllegalArgumentException("不能同時設置【clickableDateList】和【unClickableDateList】屬性！")
        }
    }

    /**
     * 創建月份，由子類別實現
     */
    protected abstract fun createMonthView(position: Int, currentMonth: Calendar, viewAttrs: ViewAttrs): BaseMonthView

    fun setOnPageChangeListener(listener: OnPageChangeListener) {
        this.onPageChangeListener = listener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = 0
        var height = 0

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        when (widthMode) {
            MeasureSpec.EXACTLY -> width = widthSize
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> width = Util.dp2px(context, 310f).toInt()
        }
        when (heightMode) {
            MeasureSpec.EXACTLY -> height = heightSize
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> height = Util.dp2px(context, 280f).toInt()
        }

        // 月份高度 = 容器 - 頭部高度 - 星期標題高度
        val monthHeight = height - headerView.measuredHeight - weekTitleView.measuredHeight
        viewPager.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(monthHeight, MeasureSpec.EXACTLY))

        setMeasuredDimension(width, height)
    }
}