
package ir.ha.meproject.utility.ui.customViews.temp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class CircleProgressView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var progress: Int = 0
    private var maxProgress: Int = 100
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val circleBounds = RectF()

    init {
        // Initialize paint objects
        circlePaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 20f
            color = ContextCompat.getColor(context, android.R.color.holo_blue_light)
        }

        textPaint.apply {
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = 64f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate() // Redraw the view
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Calculate the bounds of the circle
        val width = width.toFloat()
        val height = height.toFloat()
        val padding = 164f
        val radius = (Math.min(width, height) / 2) - padding

        circleBounds.set(
            width / 2 - radius,
            height / 2 - radius,
            width / 2 + radius,
            height / 2 + radius
        )

        // Draw the background circle
        circlePaint.color = Color.GRAY
        canvas.drawCircle(width / 2, height / 2, radius, circlePaint)

        // Draw the progress circle
        circlePaint.color = ContextCompat.getColor(context, android.R.color.holo_blue_light)
        val sweepAngle = 360 * progress / maxProgress.toFloat()
        canvas.drawArc(circleBounds, -90f, sweepAngle, false, circlePaint)

        // Draw the central label
        val progressText = "$progress%"
        canvas.drawText(progressText, width / 2, height / 2 - (textPaint.descent() + textPaint.ascent()) / 2, textPaint)
    }
}
