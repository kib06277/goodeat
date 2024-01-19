package chun.com.tw.goodeat.Bean
import android.graphics.PointF
import android.graphics.RectF

/**
 * Created by han on 2023/4/6.
 */
data class DateItem(
    val date: DateInfo, /* 日期 */
    var drawPoint: PointF, /* 文本绘制坐标 */
    var centerPoint: PointF, /* 文字中心坐标 */
    var clickBounds: RectF, /* 可点击范围 */
    var cellBounds: RectF, /* 格子区域 */
)