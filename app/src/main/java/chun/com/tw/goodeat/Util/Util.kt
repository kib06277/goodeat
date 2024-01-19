package chun.com.tw.goodeat.Util

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import chun.com.tw.goodeat.Base.DefaultHeaderView
import chun.com.tw.goodeat.Bean.RangeViewAttrs
import chun.com.tw.goodeat.Bean.ViewAttrs
import chun.com.tw.goodeat.R
import chun.com.tw.goodeat.Constant.Const
import chun.com.tw.goodeat.View.BaseHeaderView

//套件
object Util {

    fun dp2px(context: Context, px: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.resources.displayMetrics)

    inline fun <R> notNull(vararg args: Any?, block: () -> R) {
        if (args.filterNotNull().size == args.size) {
            block()
        }
    }

    /**
     * 设置控件通用属性
     */
    fun createViewAttrs(context: Context, attrs: AttributeSet): ViewAttrs {
        val viewAttrs = ViewAttrs()
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CalendarView)
        setViewAttr(context, viewAttrs, typedArray)
        typedArray.recycle()
        return viewAttrs
    }

    /**
     * 范围控件属性
     */
    fun createRangeViewAttrs(context: Context, attrs: AttributeSet): RangeViewAttrs {
        val viewAttrs = RangeViewAttrs()
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CalendarView)
        setViewAttr(context, viewAttrs, typedArray)

        viewAttrs.selectedRangeBgColor = typedArray.getColor( R.styleable.CalendarView_selected_range_bg_color, Color.parseColor("#800078D7"))
        viewAttrs.selectedRangeDayColor = typedArray.getColor( R.styleable.CalendarView_selected_range_day_color, Color.parseColor("#ffffff"))
        viewAttrs.selectedStartBgColor = typedArray.getColor( R.styleable.CalendarView_selected_start_bg_color, Color.parseColor("#0037A6"))
        viewAttrs.selectedStartDayColor = typedArray.getColor( R.styleable.CalendarView_selected_start_day_color, Color.parseColor("#ffffff"))
        viewAttrs.selectedEndBgColor = typedArray.getColor( R.styleable.CalendarView_selected_end_bg_color, Color.parseColor("#0037A6"))
        viewAttrs.selectedEndDayColor = typedArray.getColor( R.styleable.CalendarView_selected_end_day_color, Color.parseColor("#ffffff"))
        typedArray.recycle()
        return viewAttrs
    }

    /**
     * 设置控件属性
     */
    private fun setViewAttr(context: Context, viewAttrs: ViewAttrs, typedArray: TypedArray) {
        viewAttrs.weekTitleColor = typedArray.getColor(R.styleable.CalendarView_week_title_color, Color.parseColor("#666666"))
        viewAttrs.defaultColor = typedArray.getColor(R.styleable.CalendarView_default_color, Color.parseColor("#666666"))
        viewAttrs.defaultDimColor = typedArray.getColor(R.styleable.CalendarView_default_dim_color, Color.parseColor("#D3D3D3"))
        viewAttrs.weekendColor = typedArray.getColor(R.styleable.CalendarView_weekend_color, Color.parseColor("#3568B9"))
        viewAttrs.selectedBgColor = typedArray.getColor(R.styleable.CalendarView_selected_bg_color, Color.parseColor("#0037A6"))
        viewAttrs.selectedDayColor = typedArray.getColor(R.styleable.CalendarView_selected_day_color, Color.parseColor("#FFFFFF"))
        viewAttrs.selectedDayDimColor = typedArray.getColor(R.styleable.CalendarView_selected_day_dim_color, Color.parseColor("#4DFFFFFF"))
        viewAttrs.dayFontSize = typedArray.getDimension(R.styleable.CalendarView_day_font_size, dp2px(context, 16f))
        viewAttrs.weekTitleFontSize = typedArray.getDimension(R.styleable.CalendarView_week_title_font_size, dp2px(context, 16f))
        viewAttrs.weekTitleLabel = typedArray.getString(R.styleable.CalendarView_week_title_label)
        viewAttrs.headerView = typedArray.getString(R.styleable.CalendarView_header_view)
        viewAttrs.firstDayOfWeek = typedArray.getInt(R.styleable.CalendarView_first_day_of_week, 1);
    }

    /**
     * 创建header view
     */
    fun createHeaderView(context: Context, headerViewName: String?): BaseHeaderView {
        var headerView: BaseHeaderView? = null
        headerViewName?.let {
            try {
                val clazz = Class.forName(it)
                val constructor = clazz.getConstructor(Context::class.java)
                headerView = constructor.newInstance(context) as BaseHeaderView
            } catch (e: Exception) {
                e.printStackTrace()
                throw RuntimeException("header_view 必须是 com.hdev.calendar.base.BaseHeaderView子类")
            }
        }
        if (headerView == null) {
            headerView = DefaultHeaderView(context)
        }
        return headerView!!
    }

    /**
     * 获取month view tag
     */
    fun getMonthViewTag(position: Int): String {
        return "${Const.VIEW_PREFIX}_${position}"
    }
}