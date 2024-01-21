package chun.com.tw.goodeat.Util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Nullable
import java.util.Calendar

//自製時鐘
class ClockView @JvmOverloads constructor(
    private val mContext: Context,
    @Nullable attrs: AttributeSet? = null
) :
    View(mContext, attrs) {
    private var mHeight = 0
    private var mWidth = 0
    private var mPaint: Paint? = null
    private val borderPadding = 10 //外边框距离父view的padding
    private val textPadding = 42 //刻度数字距离边框的距离
    private var mCalendar: Calendar? = null
    private val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            mCalendar = Calendar.getInstance()
            //重新绘制页面
            invalidate()
            //1000ms后再刷新一次页面
            sendEmptyMessageDelayed(0, 1000)
        }
    }

    init {
        init()
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs
    )

    private fun init() {
        mPaint = Paint()
        mCalendar = Calendar.getInstance()
        mHandler.sendEmptyMessage(0)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mHeight = measuredHeight
        mWidth = measuredWidth
        mPaint!!.strokeWidth = dp2px(3).toFloat()
        mPaint!!.color = Color.BLACK
        mPaint!!.isAntiAlias = true
        mPaint!!.textAlign = Paint.Align.CENTER
        mPaint!!.strokeCap = Paint.Cap.ROUND
        mPaint!!.style = Paint.Style.STROKE
        //绘制外环
        canvas.drawCircle(
            (mWidth / 2).toFloat(),
            (mHeight / 2).toFloat(),
            (mHeight / 2 - dp2px(borderPadding)).toFloat(),
            mPaint!!
        )
        //绘制刻度,每次绘制完需要旋转一定的角度,然后继续绘制
        for (i in 0..59) {
            if (i % 5 == 0) {
                mPaint!!.strokeWidth = dp2px(3).toFloat()
                canvas.drawLine(
                    (mWidth / 2).toFloat(),
                    dp2px(borderPadding).toFloat(),
                    (mWidth / 2).toFloat(),
                    dp2px(23).toFloat(),
                    mPaint!!
                )
            } else {
                mPaint!!.strokeWidth = dp2px(1).toFloat()
                canvas.drawLine(
                    (mWidth / 2).toFloat(),
                    dp2px(borderPadding).toFloat(),
                    (mWidth / 2).toFloat(),
                    dp2px(20).toFloat(),
                    mPaint!!
                )
            }
            //一共绘制60个刻度,每次旋转360°/60
            canvas.rotate((360 / 60).toFloat(), (mWidth / 2).toFloat(), (mHeight / 2).toFloat())
        }
        mPaint!!.style = Paint.Style.FILL_AND_STROKE
        mPaint!!.strokeWidth = dp2px(1).toFloat()
        mPaint!!.textSize = dp2px(15).toFloat()
        //绘制刻度数字
        for (i in 0..11) {
            //保存当前画布状态
            canvas.save()
            //将要旋转的中心点定位到数字上,旋转的角度取决于当前数字倾斜的角度
            canvas.rotate(
                (-360 / 12 * i).toFloat(),
                (mWidth / 2).toFloat(),
                dp2px(textPadding) - mPaint!!.measureText((if (i == 0) "12" else i).toString()) / 2
            )
            //绘制数字
            canvas.drawText(
                (if (i == 0) "12" else i).toString(),
                (mWidth / 2).toFloat(),
                dp2px(textPadding).toFloat(),
                mPaint!!
            )
            //恢复被旋转的画布
            canvas.restore()
            //绕表盘中心旋转,绘制下一个刻度
            canvas.rotate((360 / 12).toFloat(), (mWidth / 2).toFloat(), (mHeight / 2).toFloat())
        }

        //获取当前时间
        val second = mCalendar!![Calendar.SECOND]
        val hour = mCalendar!![Calendar.HOUR]
        val minute = mCalendar!![Calendar.MINUTE]

        //绘制秒针
        mPaint!!.strokeWidth = dp2px(1).toFloat()
        mPaint!!.color = Color.BLACK
        canvas.save()
        canvas.rotate(
            (second * (365 / 60)).toFloat(),
            (mWidth / 2).toFloat(),
            (mHeight / 2).toFloat()
        )
        canvas.drawLine(
            (mWidth / 2).toFloat(),
            (dp2px(textPadding) + dp2px(10)).toFloat(),
            (mWidth / 2).toFloat(),
            (mHeight / 2 + dp2px(20)).toFloat(),
            mPaint!!
        )
        canvas.restore()
        //绘制分针
        mPaint!!.strokeWidth = dp2px(3).toFloat()
        mPaint!!.color = Color.BLACK
        canvas.save()
        canvas.rotate(
            (minute + second.toFloat() / 60) * 360 / 60,
            (mWidth / 2).toFloat(),
            (mHeight / 2).toFloat()
        )
        canvas.drawLine(
            (mWidth / 2).toFloat(),
            (dp2px(textPadding) + dp2px(20)).toFloat(),
            (mWidth / 2).toFloat(),
            (mHeight / 2 + dp2px(20)).toFloat(),
            mPaint!!
        )
        canvas.restore()
        //绘制时针
        mPaint!!.strokeWidth = dp2px(5).toFloat()
        mPaint!!.color = Color.BLACK
        canvas.save()
        canvas.rotate(
            (hour + minute.toFloat() / 60) * 360 / 12,
            (mWidth / 2).toFloat(),
            (mHeight / 2).toFloat()
        )
        canvas.drawLine(
            (mWidth / 2).toFloat(),
            (dp2px(textPadding) + dp2px(50)).toFloat(),
            (mWidth / 2).toFloat(),
            (mHeight / 2 + dp2px(20)).toFloat(),
            mPaint!!
        )
        canvas.restore()
        //绘制中心圆点
        mPaint!!.color = Color.RED
        mPaint!!.style = Paint.Style.FILL
        canvas.drawCircle(
            (mWidth / 2).toFloat(), (mHeight / 2).toFloat(), dp2px(5).toFloat(),
            mPaint!!
        )
    }

    private fun dp2px(dp: Int): Int {
        return dip2px(mContext, dp)
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
}