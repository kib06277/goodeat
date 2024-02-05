package chun.com.tw.goodeat.Base

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import chun.com.tw.goodeat.Bean.ClickableType
import chun.com.tw.goodeat.Bean.DateInfo
import chun.com.tw.goodeat.Bean.DateItem
import chun.com.tw.goodeat.Bean.ViewAttrs
import chun.com.tw.goodeat.Listener.OnDateSelectedListener
import chun.com.tw.goodeat.R
import chun.com.tw.goodeat.Util.DateUtil
import java.util.*

/**
 * 月份基礎類別
 * Created by han on 2023/3/5.
 */
abstract class BaseMonthView (
    context: Context,
    val monthDate: Calendar, // 月份日期
    var positionInCalendar: Int = 0, // 當前月份所在的日曆索引
    var viewAttrs: ViewAttrs
) : View(context) {

    private var dataInit = false
    // 每一格子高度
    private var cellHeight: Int = 0
    // 文字大小
    private val fontSize: Float = viewAttrs.dayFontSize
    // 日期原始數據
    private val dateList: List<DateInfo> = DateUtil.buildDateList(monthDate.timeInMillis, viewAttrs.firstDayOfWeek)
    // 星期數目
    private var weekCount = DateUtil.calcWeekCount(dateList)

    // 日期 item , 包含每個繪製座標，中心座標，日期點擊範圍
    protected lateinit var dateItemList: MutableList<DateItem>
    // 星期信息, key 為第幾星期，該星期相對當前日曆，也即當前月份的第幾行；value 為該星期(該行)包含日期
    protected lateinit var weekMap: MutableMap<Int, MutableList<DateItem>>

    // 主畫筆
    private lateinit var mainPaint: Paint
    // 選中狀態畫筆
    protected lateinit var selectedPaint: Paint
    // 高選中圓半徑
    protected var selectedRadius: Float = 0f

    // 可點擊的日期
    var clickableDateList: List<DateInfo>? = null
    var unClickableDateList: List<DateInfo>? = null

    // 日曆範圍
    var minDate: Calendar? = null
    var maxDate: Calendar? = null

    // 上下、左右間距
    var padding: Int = 0

    // 日曆點擊類型
    var clickableType: ClickableType = ClickableType.NORMAL

    // 日期選中回調
    internal var onDateSelectedListener: OnDateSelectedListener? = null

    val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.calendarlove)
    var number = 0
    private var x = 0f
    private var y = 0f

    override fun onDraw(canvas: Canvas) {
        initData()
        // 绘制高亮
        drawSelectedBg(canvas)
        // 绘制日期
        drawDays(canvas)
    }

    /**
     * 初始化數據
     */
    private fun initData() {
        if (dataInit) return
        dataInit = true

        // 初始化
        mainPaint = createPaint()
        selectedPaint = createPaint()
        // 計算日期
        dateItemList = DateUtil.buildDateItemList(context, dateList, measuredWidth, cellHeight, (mainPaint.fontMetrics.bottom + mainPaint.fontMetrics.top).toInt(), padding)
        // 星期數據
        weekMap = DateUtil.buildWeekMap(dateItemList)
        // 計算選中圓半徑
        selectedRadius = (dateItemList[0].clickBounds.bottom - dateItemList[0].clickBounds.top) / 2
        // 数据初始化完毕
        afterDataInit()
    }

    /**
     * 初始化數據完畢
     */
    protected open fun afterDataInit() {}

    /**
     * 創建畫筆
     */
    private fun createPaint(): Paint = Paint().apply {
        isAntiAlias = true
        isFilterBitmap = true
        textAlign = Paint.Align.CENTER
        textSize = fontSize
    }

    /**
     * 繪製日期
     */
    private fun drawDays(canvas: Canvas?) {
        //繪製背景
        val color = ContextCompat.getColor(context, R.color.FFe5d6c4)
        canvas?.drawColor(color)

        //繪製文字
        dateItemList.forEach {
            drawDay(canvas, it)
        }
    }

    /**
     * 绘制日期文本
     */
    protected open fun drawDay(canvas: Canvas?, dateItem: DateItem) {
        // 繪製選中效果
        var handleSelectedDate = drawSelectedDay(canvas, dateItem)

        if (!handleSelectedDate) {
            // 設置話題颜色
            setMainPaintColor(dateItem)

            // 繪製普通文本
            drawDayText(canvas, dateItem, mainPaint)

//            //繪製愛心
//            canvas?.drawBitmap(bitmap, x, y, mainPaint)
//
//            if(number < 6 ){
//                x += 150f
//                number++
//            } else {
//                y += 150f
//                x = 50f
//                number = 0
//            }
        }
    }

    /**
     * 設置畫筆颜色
     */
    private fun setMainPaintColor(dateItem: DateItem) {
        when (clickableType) {
            ClickableType.CLICKABLE -> {
                // 可點擊日期
                if (clickableDateList?.contains(dateItem.date) == true) {
                    setMonthPaintColor(dateItem)
                } else {
                    setDimPaintColor(dateItem) // 當月沒有可點擊
                }
            }
            ClickableType.UN_CLICKABLE -> {
                // 不可點擊日期
                if (unClickableDateList?.contains(dateItem.date) == true) {
                    setDimPaintColor(dateItem)
                } else {
                    setMonthPaintColor(dateItem) // 當月沒有不可點擊
                }
            }
            ClickableType.NORMAL -> {
                // 可點擊與不可點都沒有設置
                setMonthPaintColor(dateItem)
            }
        }

        // 日子是否超出範圍
        if (DateUtil.isOutOfRange(minDate, maxDate, monthDate, dateItem.date.day)) {
            setDimPaintColor(dateItem)
        }
    }

    private fun setMonthPaintColor(dateItem: DateItem) {
        // 日期為本月
        if (dateItem.date.type == DateInfo.DateType.CURRENT) {
            setNormalPaintColor(dateItem)
        } else {
            // 非本月日期
            setDimPaintColor(dateItem)
        }
    }

    private fun setNormalPaintColor(dateItem: DateItem) {
        // 是否為周末
        if (DateUtil.isWeekend(dateItem.date)) {
            mainPaint.color = viewAttrs.weekendColor!!
        } else {
            mainPaint.color = viewAttrs.defaultColor!!
        }
    }

    private fun setDimPaintColor(dateItem: DateItem) {
        mainPaint.color = viewAttrs.defaultDimColor!!
    }

    protected open fun setClickablePaintColor(date: DateInfo) {
        // 可點擊日其中是否存在該日期，如果存在，則畫筆設置高亮度
        if (clickableDateList?.contains(date) == true) {
            selectedPaint.color = viewAttrs.selectedDayColor
        } else {
            selectedPaint.color = viewAttrs.selectedDayDimColor
        }
    }

    protected open fun setUnClickablePaintColor(date: DateInfo) {
        // 不可點擊日期中是否存在該日期，如果存在，則畫筆設定灰色
        if (unClickableDateList?.contains(date) == true) {
            selectedPaint.color = viewAttrs.selectedDayDimColor
        } else {
            selectedPaint.color = viewAttrs.selectedDayColor
        }
    }

    /**
     * 繪製日期文本
     */
    protected fun drawDayText(canvas: Canvas?, dateItem: DateItem, paint: Paint) {
        canvas?.drawText("${dateItem.date.day}", dateItem.drawPoint.x, dateItem.drawPoint.y, paint)
    }

    /**
     * 繪製選中日期
     */
    protected open fun drawSelectedDay(canvas: Canvas?, dateItem: DateItem): Boolean {
        return false
    }

    /**
     * 繪製選中背景
     */
    protected open fun drawSelectedBg(canvas: Canvas?) {}

    /**
     * 選中日期
     */
    private fun onDateSelected(selectedDateItem: DateItem) {
        var changeMonth = false
        var monthPosition = this.positionInCalendar
        var type = selectedDateItem.date.type
        // 選中的日子是否為本月
        if (type != DateInfo.DateType.CURRENT) {
            // 切換月份
            changeMonth = true
            monthPosition = if (type == DateInfo.DateType.PREV) {
                this.positionInCalendar - 1
            } else {
                this.positionInCalendar + 1
            }
        }
        onDateSelected(selectedDateItem, changeMonth, monthPosition)
    }

    /**
     * 選中日期
     */
    protected open fun onDateSelected(selectedDateItem: DateItem, changeMonth: Boolean, monthPosition: Int) {}

    /**
     * 根據選中的日期，更新界面
     */
    open fun updateByDateSelected(isCurrentMonth: Boolean, dateInfo: DateInfo) {}

    /**
     * 點擊事件處理
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_UP -> {
                handleClick(event.x, event.y)
            }
        }
        return true
    }

    /**
     *  處理點擊事件
     */
    private fun handleClick(x: Float, y: Float) {
        val selectedDateItem = checkClickPoint(x, y)
        // 判斷日期是否可點擊
        selectedDateItem?.let { dateItem ->
            // 點擊的日子是否已超範圍
            if (DateUtil.isOutOfRange(minDate, maxDate, dateItem.date.toCalendar(), dateItem.date.day)) {
                // do nothing
                return
            }
            when (clickableType) {
                ClickableType.CLICKABLE -> {
                    // 可點擊日棋
                    clickableDateList?.let {
                        if (it.contains(dateItem.date)) {
                            onDateSelected(dateItem)
                        }
                    }
                }
                ClickableType.UN_CLICKABLE -> {
                    // 不可點擊日期
                    unClickableDateList?.let {
                        if (!it.contains(dateItem.date)) {
                            onDateSelected(dateItem)
                        }
                    } ?: onDateSelected(dateItem)
                }
                ClickableType.NORMAL -> {
                    // 可點擊與不可點都沒有設置
                    onDateSelected(dateItem)
                }
            }
        }
    }

    /**
     * 檢查使用者點擊座標在哪個日期上
     */
    private fun checkClickPoint(x: Float, y: Float): DateItem? {
        for (item in dateItemList) {
            if (item.clickBounds.contains(x, y)) {
                return item
            }
        }
        return null
    }

    /**
     * 計算選取圓的半徑
     */
    private fun calcSelectedRadius(): Float {
        return cellHeight / 2.2f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        var resultWidth = widthSize
        var resultHeight = heightSize
        // 月份高度 = 星期總行高 + 上下邊距
        cellHeight = ((heightSize - padding * 1.2f) / weekCount).toInt()
        setMeasuredDimension(resultWidth, resultHeight)
    }
}