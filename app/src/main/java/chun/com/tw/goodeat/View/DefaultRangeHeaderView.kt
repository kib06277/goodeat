package chun.com.tw.goodeat.View

import android.content.Context
import android.widget.TextView
import chun.com.tw.goodeat.Base.DefaultHeaderView
import chun.com.tw.goodeat.Bean.DateInfo
import chun.com.tw.goodeat.Listener.IDateRange
import chun.com.tw.goodeat.R

/**
 * 包含日期範圍的頭部
 */
class DefaultRangeHeaderView(
    context: Context
) : DefaultHeaderView(context), IDateRange {

    private lateinit var dateRangeLabel: TextView

    override fun dateRange(startDate: DateInfo?, endDate: DateInfo?) {
        if (startDate == null && endDate == null) {
            dateRangeLabel.text = ""
        } else if (startDate != null && endDate != null) {
            dateRangeLabel.text = "${startDate.format()} -- ${endDate.format()}"
        } else if (startDate != null) {
            dateRangeLabel.text = startDate.format()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.default_range_header_view
    }

    override fun init() {
        super.init()
        dateRangeLabel = findViewById(R.id.date_range_label)
    }
}