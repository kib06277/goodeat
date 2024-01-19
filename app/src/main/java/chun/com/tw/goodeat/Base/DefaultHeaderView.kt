package chun.com.tw.goodeat.Base

import android.content.Context
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import chun.com.tw.goodeat.R
import chun.com.tw.goodeat.View.BaseHeaderView

/**
 * 默認頭部
 */
open class DefaultHeaderView(
    context: Context
) : BaseHeaderView(context), OnClickListener {

    private lateinit var titleLabel: TextView
    private lateinit var titleLabel_1: TextView
    private lateinit var prevMonth: TextView
    private lateinit var nextMonth: TextView

    override fun getLayoutId(): Int {
        return R.layout.default_header_view
    }

    /**
     * 初始化
     */
    override fun init() {
        titleLabel = findViewById(R.id.title_label)
        titleLabel_1 = findViewById(R.id.title_label_1)
        prevMonth = findViewById(R.id.prev_month)
        nextMonth = findViewById(R.id.next_month)

        prevMonth.setOnClickListener(this)
        nextMonth.setOnClickListener(this)
    }

    /**
     * 更新標題
     */
    override fun updateTitle(year: Int, month: Int) {
        titleLabel.text = "${month}月"
        titleLabel_1.text = "${year}"
    }

    /**
     * 處理上下按鈕
     */
    override fun handlePrevNext(hasPrev: Boolean, hasNext: Boolean) {
        if (hasPrev) {
            prevMonth.visibility = View.VISIBLE
        } else {
            prevMonth.visibility = View.INVISIBLE
        }
        if (hasNext) {
            nextMonth.visibility = View.VISIBLE
        } else {
            nextMonth.visibility = View.INVISIBLE
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.prev_month -> {
                listener?.prevMonth()
            }
            R.id.next_month -> {
                listener?.nextMonth()
            }
        }
    }
}