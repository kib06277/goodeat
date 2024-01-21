package chun.com.tw.goodeat.View
import android.content.Context
import android.util.AttributeSet
import chun.com.tw.goodeat.Base.BaseCalendarView
import chun.com.tw.goodeat.Base.BaseMonthView
import chun.com.tw.goodeat.Bean.DateInfo
import chun.com.tw.goodeat.Bean.ViewAttrs
import chun.com.tw.goodeat.Listener.OnDateSelectedListener
import chun.com.tw.goodeat.Listener.OnSingleDateSelectedListener
import chun.com.tw.goodeat.View.Month.SingleMonthView
import java.util.*

/**
 * 單選日曆控件
 */
class SingleCalendarView(
    context: Context,
    attrs: AttributeSet
) : BaseCalendarView(context, attrs) {

    private var listener: OnSingleDateSelectedListener? = null

    /**
     * 設置選中日期
     */
    fun setSelectedDate(selectedDate: DateInfo) {
        setDateRange(selectedTimeInMillis = selectedDate.timeInMillis())
    }

    /**
     * 創建月份
     */
    override fun createMonthView(position: Int, currentMonth: Calendar, viewAttrs: ViewAttrs): BaseMonthView {
        val monthView = SingleMonthView(context, currentMonth, position, viewAttrs)
        monthView.selectedDate = DateInfo().toDate(selectedDate)
        monthView.onDateSelectedListener = object : OnDateSelectedListener {
            override fun onDateSelected(dateInfo: DateInfo, changeMonth: Boolean, monthPosition: Int) {
                updateDateSelected(dateInfo, changeMonth, monthPosition)
                listener?.let { it(this@SingleCalendarView, dateInfo) }
            }
        }
        return monthView
    }

    fun setOnSingleDateSelectedListener(listener: OnSingleDateSelectedListener) {
        this.listener = listener
    }
}