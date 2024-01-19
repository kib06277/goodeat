package chun.com.tw.goodeat.Adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import chun.com.tw.goodeat.Base.BaseMonthView
import chun.com.tw.goodeat.Constant.Const
import java.util.*

/**
 * 日曆調適器
 */
internal class CalendarAdapter(
    private val minDate: Calendar,
    private val createBlock: (Int, Calendar) -> BaseMonthView
) : PagerAdapter() {

    // 月份個數
    var monthCount = 0

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val currentMonth = Calendar.getInstance()
        currentMonth[Calendar.YEAR] = getYearFromPosition(position)
        currentMonth[Calendar.MONTH] = getMonthFromPosition(position)
        currentMonth[Calendar.DAY_OF_MONTH] = 1
        val monthView = createBlock(position, currentMonth)
        container.addView(monthView)
        return monthView
    }

    override fun destroyItem(container: ViewGroup, position: Int, anyObj: Any) {
        container.removeView(anyObj as View)
    }

    /**
     * 設置日期範圍
     */
    fun setRange(min: Calendar, max: Calendar) {
        val minDate = Calendar.getInstance()
        val maxDate = Calendar.getInstance()
        minDate.timeInMillis = min.timeInMillis
        maxDate.timeInMillis = max.timeInMillis
        val diffYear = maxDate[Calendar.YEAR] - minDate[Calendar.YEAR]
        val diffMonth = maxDate[Calendar.MONTH] - minDate[Calendar.MONTH]
        monthCount = diffMonth + Const.MONTHS * diffYear + 1

        notifyDataSetChanged()
    }

    /**
     * 根據 position 獲取月份
     */
    private fun getMonthFromPosition(position: Int): Int {
        return (position + minDate[Calendar.MONTH]) % Const.MONTHS
    }

    /**
     * 根據 position 獲取年份
     */
    private fun getYearFromPosition(position: Int): Int {
        val yearOffset: Int = (position + minDate[Calendar.MONTH]) / Const.MONTHS
        return yearOffset + minDate[Calendar.YEAR]
    }

    override fun isViewFromObject(view: View, anyObj: Any) = view === anyObj

    override fun getCount(): Int {
        return monthCount
    }

    override fun getItemPosition(obj: Any): Int {
        // 强制 ViewPager 不缓存，每次滑動都更新畫面
        return POSITION_NONE
    }
}