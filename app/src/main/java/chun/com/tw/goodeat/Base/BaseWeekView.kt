package chun.com.tw.goodeat.Base

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * 星期基礎類別
 */
abstract class BaseWeekView(
    context: Context,
    attributeSet: AttributeSet
) : View(context, attributeSet) {

    override fun onDraw(canvas: Canvas) {
        if (canvas != null) {
            super.onDraw(canvas)
        }
    }
}