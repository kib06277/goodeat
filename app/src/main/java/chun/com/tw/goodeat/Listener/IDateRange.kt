package chun.com.tw.goodeat.Listener

import chun.com.tw.goodeat.Bean.DateInfo

//日期範圍
interface IDateRange {
    fun dateRange(start: DateInfo?, endDate: DateInfo?)
}