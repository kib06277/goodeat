package chun.com.tw.goodeat.Listener

import chun.com.tw.goodeat.Bean.DateInfo


 //选中日期，日历控件内部使用
internal interface OnDateSelectedListener {

    /**
     * @param dateInfo 日期信息
     * @param changeMonth 是否需要切换月份
     * @param monthPosition 月份所在的索引
     */
    fun onDateSelected(
        dateInfo: DateInfo,
        changeMonth: Boolean,
        monthPosition: Int
    )
}