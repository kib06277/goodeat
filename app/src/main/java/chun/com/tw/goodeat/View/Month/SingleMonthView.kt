package chun.com.tw.goodeat.View.Month
import android.content.Context
import android.graphics.Canvas
import chun.com.tw.goodeat.Base.BaseMonthView
import chun.com.tw.goodeat.Bean.DateInfo
import chun.com.tw.goodeat.Bean.DateItem
import chun.com.tw.goodeat.Bean.ViewAttrs
import java.util.*

/**
<<<<<<< Updated upstream
 * 默认单选月份View
=======
 * 預設單選月份View
>>>>>>> Stashed changes
 */
internal open class SingleMonthView(
    context: Context,
    monthDate: Calendar, // 月份日期
    positionInCalendar: Int = 0, // 目前月份所在的日曆索引
    viewAttrs: ViewAttrs,
) : BaseMonthView(context, monthDate, positionInCalendar, viewAttrs) {

    // 選取的日期資訊
    protected var selectedDateItem: DateItem? = null
    // 選中的日子
    var selectedDate: DateInfo? = null

    override fun afterDataInit() {
        super.afterDataInit()
        // 設定預設選取的日期
        dateItemList.forEach {
            if (it.date == selectedDate) {
                selectedDateItem = it
            }
        }
    }

    /**
     * 繪製選取日期
     */
    override fun drawSelectedDay(canvas: Canvas?, dateItem: DateItem): Boolean {
        super.drawSelectedDay(canvas, dateItem)
        return drawSelectedDay(canvas, dateItem, selectedDateItem)
    }

    /**
     * 繪製選取日棋
     */
    protected open fun drawSelectedDay(canvas: Canvas?, dateItem: DateItem, selectedDateItem: DateItem?): Boolean {
        // 本月份的日子才繪製高亮
        return selectedDateItem?.let {
            if (it.date.type == DateInfo.DateType.CURRENT && it === dateItem) {
                selectedPaint.color = viewAttrs.selectedDayColor
                drawDayText(canvas, dateItem, selectedPaint)
                true
            } else {
                false
            }
        } ?: false
    }

    /**
     * 繪製選中背景
     */
    override fun drawSelectedBg(canvas: Canvas?) {
        super.drawSelectedBg(canvas)
        drawSelectedBg(canvas, selectedDateItem)
    }

    /**
     * 繪製選中背景
     */
    protected open fun drawSelectedBg(canvas: Canvas?, selectedDateItem: DateItem?) {
        super.drawSelectedBg(canvas)
        selectedDateItem?.let {
            // 本月份的日子才繪製高亮
            if (it.date.type == DateInfo.DateType.CURRENT) {
                selectedPaint.color = viewAttrs.selectedBgColor
                canvas?.drawCircle(it.centerPoint.x, it.centerPoint.y, selectedRadius, selectedPaint)
            }
        }
    }
    
    /**
     * 選取日期，SingleCalendarView.onDateSelected()，再呼叫本類別的updateByDateSelected
     */
    override fun onDateSelected(selectedDateItem: DateItem, changeMonth: Boolean, monthPosition: Int) {
        super.onDateSelected(selectedDateItem, changeMonth, monthPosition)
        // 選中的日子是否為本月
        if (selectedDateItem.date.type != DateInfo.DateType.CURRENT) {
            // 取消本月選中
            this.selectedDateItem = null
        }
        // 日期選擇回調
        onDateSelectedListener?.onDateSelected(selectedDateItem.date, changeMonth, monthPosition)
    }

    /**
     * 根據選中的日期，更新介面
     */
    override fun updateByDateSelected(isCurrentMonth: Boolean, dateInfo: DateInfo) {
        super.updateByDateSelected(isCurrentMonth, dateInfo)
        if (isCurrentMonth) {
            // 取消選取狀態
            selectedDate = null
            selectedDateItem = null
            for (item in dateItemList) {
                if (item.date == dateInfo) {
                    selectedDate = dateInfo
                    selectedDateItem = item
                    break
                }
            }
        } else {
            selectedDate = null
            selectedDateItem = null
        }
    }
}