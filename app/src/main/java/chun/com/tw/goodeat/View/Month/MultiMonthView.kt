package chun.com.tw.goodeat.View.Month

import android.content.Context
import android.graphics.Canvas
import chun.com.tw.goodeat.Bean.DateInfo
import chun.com.tw.goodeat.Bean.DateItem
import chun.com.tw.goodeat.Bean.ViewAttrs
import java.util.Calendar


/**
 * 多选月份View
 * Created by han on 2023/4/27.
 */
internal class MultiMonthView(
    context: Context,
    monthDate: Calendar,
    positionInCalendar: Int = 0, // 当前月份所在的日历索引
    viewAttrs: ViewAttrs,
    var selectedDateList: MutableList<DateInfo>
) : SingleMonthView(context, monthDate, positionInCalendar, viewAttrs) {

    // 保存选中的日期列表项
    private val selectedDateItemList: MutableList<DateItem> = mutableListOf()

    override fun afterDataInit() {
        dateItemList.forEach {
            if (it.date == selectedDate) {
                selectedDateItem = it
            }
            if (selectedDateList.contains(it.date)) {
                selectedDateItemList.add(it)
            }
        }
    }

    /**
     * 绘制选中日期
     */
    override fun drawSelectedDay(canvas: Canvas?, dateItem: DateItem): Boolean {
        if (selectedDateItemList.contains(dateItem)) {
            return drawSelectedDay(canvas, dateItem, dateItem)
        }
        return false
    }

    /**
     * 绘制选中背景
     */
    override fun drawSelectedBg(canvas: Canvas?) {
        for (item in selectedDateItemList) {
            drawSelectedBg(canvas, item)
        }
    }

    /**
     * 根据选中的日期，更新界面
     */
    override fun updateByDateSelected(isCurrentMonth: Boolean, dateInfo: DateInfo) {
        super.updateByDateSelected(isCurrentMonth, dateInfo)
        if (isCurrentMonth) {
            var dateItem = dateItemList.filter {it.date == dateInfo }
            // 取消选中
            if (selectedDateList.contains(dateInfo)) {
                selectedDateItemList.remove(dateItem[0])
                selectedDateList.remove(dateInfo)
            } else {
                // 设置选中
                selectedDateItemList.add(dateItem[0])
                selectedDateList.add(dateInfo)
            }
        }
    }
}