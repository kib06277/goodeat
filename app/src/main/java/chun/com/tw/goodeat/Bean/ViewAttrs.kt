package chun.com.tw.goodeat.Bean
/**
 * 控件属性
 */
open class ViewAttrs {

    // 星期標題颜色
    var weekTitleColor = 0

    // 星期字體大小
    var weekTitleFontSize = 0f

    // 星期標題，以頓號分割，如：一、二、三、四、五、六、日
    var weekTitleLabel: String? = ""

    // 一周的第一天
    var firstDayOfWeek: Int = 0

    // 日期字體大小
    var dayFontSize = 0f

    // 默認文字颜色
    var defaultColor = 0

    // 默認文字灰色
    var defaultDimColor = 0

    // 周未颜色
    var weekendColor = 0

    // 選中背景色
    var selectedBgColor = 0

    // 選中 day 颜色
    var selectedDayColor = 0

    // 全限定類名； 通過反射創建 header view
    var headerView: String? = null

    // 範圍選擇握者按星期選擇時，該日期不是能選擇，但該日子還在範圍或者星期之間
    var selectedDayDimColor = 0
}