package chun.com.tw.goodeat.Base

import android.content.Context
import android.util.AttributeSet
import chun.com.tw.goodeat.Bean.DateInfo
import chun.com.tw.goodeat.Bean.ViewAttrs
import chun.com.tw.goodeat.Listener.OnDateSelectedListener
import chun.com.tw.goodeat.Listener.OnMultiDateSelectedListener
import chun.com.tw.goodeat.Tools.BaseCalendarView
import chun.com.tw.goodeat.View.Month.MultiMonthView
import java.util.*

/**
 *  多選擇日期控件
 */
class MultiCalendarView(
    context: Context,
    attrs: AttributeSet
) : BaseCalendarView(context, attrs) {

    private var listener: OnMultiDateSelectedListener? = null

    // 存放所有選取的日期
    var selectedDateList = mutableListOf<DateInfo>()
        set(value) {
            selectedDateList.addAll(value)
        }

    /**
     * 創建月份
     */
    override fun createMonthView(position: Int, currentMonth: Calendar, viewAttrs: ViewAttrs): BaseMonthView {
        val monthView = MultiMonthView(context, currentMonth, position, viewAttrs, selectedDateList)
        monthView.onDateSelectedListener = object : OnDateSelectedListener {
            override fun onDateSelected(dateInfo: DateInfo, changeMonth: Boolean, monthPosition: Int) {
                updateDateSelected(dateInfo, changeMonth, monthPosition)
                listener?.let { it(this@MultiCalendarView, dateInfo, selectedDateList) }
            }
        }
        return monthView
    }

    fun setOnMultiDateSelectedListener(listener: OnMultiDateSelectedListener) {
        this.listener = listener
    }
}