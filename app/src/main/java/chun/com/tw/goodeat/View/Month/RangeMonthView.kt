package chun.com.tw.goodeat.View.Month
import android.content.Context
import android.graphics.Canvas
import chun.com.tw.goodeat.Base.BaseMonthView
import chun.com.tw.goodeat.Bean.ClickableType
import chun.com.tw.goodeat.Bean.DateInfo
import chun.com.tw.goodeat.Bean.DateItem
import chun.com.tw.goodeat.Bean.RangeViewAttrs
import java.util.*

/**
<<<<<<< Updated upstream
 * 日期区间选择
=======
 * 日期區間選擇
>>>>>>> Stashed changes
 */
internal class RangeMonthView(
    context: Context,
    monthDate: Calendar, // 月份日期
    positionInCalendar: Int = 0, // 目前月份所在的日曆索引
    private var rangeViewAttrs: RangeViewAttrs,
) : BaseMonthView(context, monthDate, positionInCalendar, rangeViewAttrs) {

    // 開始日期
    private var startDate: DateInfo? = null
    // 结束日期
    private var endDate: DateInfo? = null

    private fun setNormalPaintColor(date: DateInfo) {
        selectedPaint.color = rangeViewAttrs.selectedRangeDayColor
    }

    override fun setClickablePaintColor(date: DateInfo) {
        // 可點選日期中是否存在該日期，如果存在，則畫筆設定為高亮
        if (clickableDateList?.contains(date) == true) {
            selectedPaint.color = rangeViewAttrs.selectedRangeDayColor
        } else {
            selectedPaint.color = viewAttrs.selectedDayDimColor
        }
    }

    override  fun setUnClickablePaintColor(date: DateInfo) {
        // 不可點擊日期中是否存在該日期，如果存在，則畫筆設定灰色
        if (unClickableDateList?.contains(date) == true) {
            selectedPaint.color = viewAttrs.selectedDayDimColor
        } else {
            selectedPaint.color = rangeViewAttrs.selectedRangeDayColor
        }
    }

    /**
     * 繪製選中日期
     */
    override fun drawSelectedDay(canvas: Canvas?, dateItem: DateItem): Boolean {
        if (startDate != null && endDate != null) {
            var handleSelectedDate = false
            // 當前月份日期
            if (dateItem.date.type == DateInfo.DateType.CURRENT) {
                // 設定中間選取區域字體顏色
                if (dateItem.date > startDate!! && dateItem.date < endDate!!) {
                    when (clickableType) {
                        ClickableType.NORMAL -> setNormalPaintColor(dateItem.date)
                        ClickableType.CLICKABLE -> setClickablePaintColor(dateItem.date)
                        ClickableType.UN_CLICKABLE -> setUnClickablePaintColor(dateItem.date)
                    }
                    handleSelectedDate = true
                } else if (dateItem.date == startDate!!) {
                    // 設定開始日期字體顏色
                    selectedPaint.color = rangeViewAttrs.selectedStartDayColor
                    handleSelectedDate = true
                } else if ( dateItem.date == endDate!!) {
                    // 設定結束日期字體顏色
                    selectedPaint.color = rangeViewAttrs.selectedEndDayColor
                    handleSelectedDate = true
                } else {
                    // 默認顏色
                    selectedPaint.color = rangeViewAttrs.defaultColor
                }
            } else {
                // 其他月份日期
                // 默認颜色
                selectedPaint.color = rangeViewAttrs.defaultDimColor
            }
            drawDayText(canvas, dateItem, selectedPaint)
            return handleSelectedDate
        } else if (startDate != null) {
            // 本月份的日子才繪製高亮
            if (startDate?.type == dateItem.date.type && startDate!! == dateItem.date) {
                selectedPaint.color = rangeViewAttrs.selectedStartDayColor
                drawDayText(canvas, dateItem, selectedPaint)
                return true
            }
        }

        return false
    }

    /**
     * 繪製選取背景
     */
    override fun drawSelectedBg(canvas: Canvas?) {
        super.drawSelectedBg(canvas)
        // 繪製區間
        if (startDate != null && endDate != null) {
            selectedPaint.color = viewAttrs.selectedBgColor
            dateItemList.forEach {
                if (it.date.type == DateInfo.DateType.CURRENT) {
                    // 繪製左半圓
                    drawLeftArcBg(canvas, it)
                    // 繪製區間背景
                    drawRangeBg(canvas, it)
                    // 繪製右半圓
                    drawRightArcBg(canvas, it)
                }
            }
        } else if (startDate != null) {
            dateItemList.forEach {
                if (startDate?.type == it.date.type && startDate!! == it.date) {
                    selectedPaint.color = rangeViewAttrs.selectedStartBgColor
                    canvas?.drawCircle(it.centerPoint.x, it.centerPoint.y, selectedRadius, selectedPaint)
                }
            }
        }
    }

    /**
     * 繪製區間背景
     */
    private fun drawRangeBg(canvas: Canvas?, dateItem: DateItem) {
        // 在 startDate 和 endDate 之間的日期繪製矩形
        selectedPaint.color = rangeViewAttrs.selectedRangeBgColor
        if (dateItem.date > startDate!! && dateItem.date < endDate!!) {
            canvas?.drawRect(dateItem.cellBounds.left, dateItem.clickBounds.top,
                dateItem.cellBounds.right, dateItem.clickBounds.bottom, selectedPaint)
        }
    }

    /**
     * 繪製左半圓
     */
    private fun drawLeftArcBg(canvas: Canvas?, dateItem: DateItem) {
        //  繪製開始日期
        if (dateItem.date == startDate) {
            // 選取的日期與下一個日期之間的空白位置
            selectedPaint.color = rangeViewAttrs.selectedRangeBgColor
            canvas?.drawRect(dateItem.clickBounds.right, dateItem.clickBounds.top,
                dateItem.clickBounds.right + (dateItem.cellBounds.right - dateItem.clickBounds.right),
                dateItem.clickBounds.bottom, selectedPaint)

            // 左半圓
            selectedPaint.color = rangeViewAttrs.selectedStartBgColor
            canvas?.drawArc(dateItem.clickBounds.left, dateItem.clickBounds.top,
                dateItem.clickBounds.right, dateItem.clickBounds.bottom, -90f,
                -180f, true, selectedPaint)
            // 左半圓後面的位置
            canvas?.drawRect(dateItem.centerPoint.x - 1, dateItem.clickBounds.top,
                dateItem.clickBounds.right, dateItem.clickBounds.bottom, selectedPaint)
        }
    }

    /**
     * 繪製右半圓
     */
    private fun drawRightArcBg(canvas: Canvas?, dateItem: DateItem) {
        // 繪製结束日期
        if (dateItem.date == endDate) {
            // 選取的日期與前一個日期之間的空白位置
            selectedPaint.color = rangeViewAttrs.selectedRangeBgColor
            canvas?.drawRect(dateItem.cellBounds.left, dateItem.clickBounds.top,
                dateItem.cellBounds.left + (dateItem.cellBounds.right - dateItem.clickBounds.right),
                dateItem.clickBounds.bottom, selectedPaint)

            // 右半圓前面的位置
            selectedPaint.color = rangeViewAttrs.selectedEndBgColor
            canvas?.drawRect(dateItem.clickBounds.left, dateItem.clickBounds.top,
                dateItem.centerPoint.x + 1, dateItem.clickBounds.bottom, selectedPaint)
            // 右半圓
            canvas?.drawArc(dateItem.clickBounds.left, dateItem.clickBounds.top,
                dateItem.clickBounds.right, dateItem.clickBounds.bottom, -90f,
                180f, true, selectedPaint)
        }

    }

    /**
     * 選中日期，RangeCalendarView.onDateSelected()
     */
    override fun onDateSelected(selectedDateItem: DateItem, changeMonth: Boolean, monthPosition: Int) {
        super.onDateSelected(selectedDateItem, changeMonth, monthPosition)
        var selectedDate = selectedDateItem.date
        var newSelectedDate = DateInfo(selectedDate.year, selectedDate.month, selectedDate.day)
        // 如果選擇的日期不是當月的日期
        if (selectedDateItem.date.type != DateInfo.DateType.CURRENT) {
            newSelectedDate.type = DateInfo.DateType.CURRENT
        }
        // 日期选择回调，回调到RangeCalendarView
        onDateSelectedListener?.onDateSelected(newSelectedDate, changeMonth, monthPosition)
    }

    /**
     * 設定日期範圍
     */
    fun setDateRange(startDate: DateInfo?, endDate: DateInfo?) {
        this.startDate = startDate
        this.endDate = endDate
    }
}