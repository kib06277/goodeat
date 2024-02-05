package chun.com.tw.goodeat.View

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.marginTop
import chun.com.tw.goodeat.Constant.Const

abstract class BaseHeaderView(
    context: Context,
    attrs: AttributeSet?
) : FrameLayout(context, attrs) {

    // 左右間距
    private var padding: Int = 0
    lateinit var rootView: ViewGroup

    var listener: HeaderViewListener? = null

    constructor(context: Context): this(context, null)

    init {
        initView()
    }

    private fun initView() {
        rootView = LayoutInflater.from(context).inflate(getLayoutId(), this, false) as ViewGroup
        addView(rootView)
        init()
    }

    /**
     * 初始化
     */
    protected abstract fun init()

    /**
     * 获取布局id，如：R.layout.default_header_view
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 更新标题
     */
    open fun updateTitle(year: Int, month: Int) {}

    /**
     * 處理上下按鈕
     * @param hasPrev 是否有上一页
     * @param hasNext 是否有下一页
     */
    open fun handlePrevNext(hasPrev: Boolean, hasNext: Boolean) {}

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            setPadding()
        }
    }

    /**
     * 設置左右間距
     */
    open fun setPadding() {
        //每一個格子大小除以 2 ，取得第一格 X 軸中心位置
        //padding = measuredWidth / Const.COLUMNS_PER_WEEK / 2
        //rootView.setPadding(padding, 0, padding, 0)
        rootView.setPadding(0, 0, 0, 0)
    }

    interface HeaderViewListener {

        /**
         * 上月
         */
        fun prevMonth()

        /**
         * 下月
         */
        fun nextMonth()
    }
}