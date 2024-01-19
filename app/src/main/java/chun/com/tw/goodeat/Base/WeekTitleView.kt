package chun.com.tw.goodeat.Base

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import chun.com.tw.goodeat.Bean.ViewAttrs
import chun.com.tw.goodeat.Constant.Const
import chun.com.tw.goodeat.Util.Util

/**
 * 星期標題控件
 */
class WeekTitleView(
    context: Context,
    var viewAttr: ViewAttrs,
) : View(context) {

    companion object {
        // 星期标题
        private val WEEK_TITLE = listOf("一", "二", "三", "四", "五", "六", "日")
    }

    private var cellWidth: Int = 0
    private val cellHeight: Int = Util.dp2px(context, 38f).toInt()

    private var dataInit = false
    private lateinit var paint: Paint
    private var weekTitles = mutableListOf<String>()

    // 左右间距
    var padding: Int = 0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        initData()
        drawTitle(canvas)
    }

    /**
     * 初始化數據
     */
    private fun initData() {
        if (dataInit) return
        initPaint()
        initCellWidth()
        initWeekTitle()
        dataInit = true
    }

    private fun initPaint() {
        paint = Paint().apply {
            isAntiAlias = true
            isFilterBitmap = true
            textAlign = Paint.Align.CENTER
            textSize = viewAttr.weekTitleFontSize
        }
    }

    private fun initCellWidth() {
        // 計算格子寬度
        cellWidth = (measuredWidth - padding * 2f).toInt() / Const.COLUMNS_PER_WEEK
    }

    private fun initWeekTitle() {
        viewAttr.weekTitleLabel?.let {
            weekTitles.addAll(it.split("、"))
        }
        if (weekTitles.size != WEEK_TITLE.size) {
            weekTitles.addAll(WEEK_TITLE)
        }
    }

    /**
     * 繪製星期標題
     */
    private fun drawTitle(canvas: Canvas?) {
        paint.color = viewAttr.weekTitleColor
        for (index in weekTitles.indices) {
            val textLeft = cellWidth / 2.0f + index * cellWidth + padding
            val textTop = cellHeight / 2.0f - (paint.fontMetrics.bottom + paint.fontMetrics.top) / 2
            canvas?.drawText(weekTitles[index], textLeft, textTop, paint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(cellHeight, MeasureSpec.EXACTLY))
    }
}