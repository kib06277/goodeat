package chun.com.tw.goodeat.View.Month

import android.content.Context
import android.graphics.Canvas
import chun.com.tw.goodeat.Bean.ClickableType
import chun.com.tw.goodeat.Bean.DateInfo
import chun.com.tw.goodeat.Bean.DateItem
import chun.com.tw.goodeat.Bean.ViewAttrs
import chun.com.tw.goodeat.Util.Util
import java.util.*

/**
<<<<<<< Updated upstream
 * 月份View，可选中某一星期
=======
 * 月份 View，可選中某一星期
>>>>>>> Stashed changes
 */
internal class WeekMonthView(
    context: Context,
    monthDate: Calendar, // 月份日期
    positionInCalendar: Int = 0, // 当前月份所在的日历索引
    viewAttrs: ViewAttrs,
) : SingleMonthView(context, monthDate, positionInCalendar, viewAttrs) {

    // 選取的日子(selectedDate)所在的星期的第一天
    var startDateItem: DateItem? = null
        private set
    // 選取的日子(selectedDate)所在的星期的最後一天
    var endDateItem: DateItem? = null
        private set
    // 圓角
    private var roundCorners: Float = Util.dp2px(context, 30f)
    // 某一周的 dateItem
    private var weekDateItemList: List<DateItem>? = null

    override fun afterDataInit() {
        super.afterDataInit()
        // 資料初始化完畢後，計算開始和結束日期
        selectedDateItem?.let { calcStartAndEndDate(it) }
    }

    /**
     * 繪製選取日期
     */
    override fun drawSelectedDay(canvas: Canvas?, dateItem: DateItem): Boolean {
        // 本月份的日子才繪製高亮
        if (selectedDateItem?.date?.type == DateInfo.DateType.CURRENT) {
            weekDateItemList?.let { dataItemList ->
                if (dataItemList.contains(dateItem)) {
                    when (clickableType) {
                        ClickableType.NORMAL -> selectedPaint.color = viewAttrs.selectedDayColor
                        ClickableType.CLICKABLE -> setClickablePaintColor(dateItem.date)
                        ClickableType.UN_CLICKABLE -> setUnClickablePaintColor(dateItem.date)
                    }
                    drawDayText(canvas, dateItem, selectedPaint)
                    return true
                }
            }
        }
        return false
    }

    /**
     * 繪製選中背景
     */
    override fun drawSelectedBg(canvas: Canvas?) {
        if (selectedDateItem?.date?.type == DateInfo.DateType.CURRENT) {
            // 本月份的日子才繪製高亮
            if (startDateItem != null && endDateItem != null) {
                selectedPaint.color = viewAttrs.selectedBgColor
                canvas?.drawRoundRect(startDateItem!!.clickBounds.left, startDateItem!!.clickBounds.top,
                    endDateItem!!.clickBounds.right, endDateItem!!.clickBounds.bottom, roundCorners, roundCorners, selectedPaint)
            }
        }
    }

    /**
     * 選取日期，WeekCalendarView.onDateSelected()，再呼叫本類別的 updateByDateSelected
     */
    override fun onDateSelected(selectedDateItem: DateItem, changeMonth: Boolean, monthPosition: Int) {
        calcStartAndEndDate(selectedDateItem)
        super.onDateSelected(selectedDateItem, changeMonth, monthPosition)
    }

    /**
     * 選取的日期是所在的星期，取得該星期的起始和結束日期
     */
    private fun calcStartAndEndDate(selectedDateItem: DateItem) {
        weekMap.values.filter {
            it.contains(selectedDateItem)
        }.let {
            this.weekDateItemList = it[0]
            this.weekDateItemList?.let { dateItemList ->
                if (dateItemList.isNotEmpty()) {
                    this.startDateItem = dateItemList[0]
                    this.endDateItem = dateItemList[dateItemList.size - 1]
                }
            }
        }
    }

    /**
     * 根據選中的日期，更新介面
     */
    override fun updateByDateSelected(isCurrentMonth: Boolean, dateInfo: DateInfo) {
        super.updateByDateSelected(isCurrentMonth, dateInfo)
        if (isCurrentMonth) {
            selectedDateItem?.let {
                calcStartAndEndDate(it)
            }
        } else {
            selectedDate = null
            selectedDateItem = null
            startDateItem = null
            endDateItem = null
            weekDateItemList = null
        }
    }
}