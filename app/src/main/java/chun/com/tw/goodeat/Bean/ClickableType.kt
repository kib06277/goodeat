package chun.com.tw.goodeat.Bean

/**
 * 日曆可點選類型
 */
enum class ClickableType {

    /**
     * 设置了可点击，日历只包含可点击日期
     */
    CLICKABLE,

    /**
     * 设置了不可点击，日历包含不可点击的日期
     */
    UN_CLICKABLE,

    /**
     * 未设置点击或者可击
     */
    NORMAL
}