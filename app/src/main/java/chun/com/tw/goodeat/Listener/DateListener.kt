package chun.com.tw.goodeat.Listener
import chun.com.tw.goodeat.Base.MultiCalendarView
import chun.com.tw.goodeat.Bean.DateInfo
import chun.com.tw.goodeat.Tools.BaseCalendarView
import chun.com.tw.goodeat.View.SingleCalendarView

/**
 * 单选接口回调
 */
typealias OnSingleDateSelectedListener = (
    view: SingleCalendarView, // 日历控件
    selectedDate: DateInfo // 选中的日期
) -> Unit

/**
 * 按星期选，区域选择接口回调
 */
typealias OnDateRangeSelectedListener = (
    view: BaseCalendarView, // 日历控件
    selectedDate: DateInfo, // 选中的日期
    startDate: DateInfo, // 开始日期
    endDate: DateInfo // 结束日期
) -> Unit

/**
 * 多选
 */
typealias OnMultiDateSelectedListener = (
    view: MultiCalendarView, // 日历控件
    clickedDate: DateInfo, // 当前当点的日期，选中或者取消选中
    dateList: List<DateInfo>, // 日期列表
) -> Unit