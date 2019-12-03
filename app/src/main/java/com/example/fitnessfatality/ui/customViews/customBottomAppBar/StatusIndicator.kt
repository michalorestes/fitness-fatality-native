package com.example.fitnessfatality.ui.customViews.customBottomAppBar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class StatusIndicator : View {
    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FFFFFF")
        style = Paint.Style.FILL

    }

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#BFBFBF")
        style = Paint.Style.STROKE
        strokeWidth = 5f

    }

    private val desiredWidth = 100
    private val desiredHeight = 100

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas?) {
        val cx = (width / 2).toFloat()
        val cy = (height / 2).toFloat()
        val fixedPadding = 5
        val radios = cy - fixedPadding

        canvas?.apply {
            drawCircle(cx, cy, radios, strokePaint)
            drawCircle(cx, cy, radios, fillPaint)
//            setBackgroundColor(Color.parseColor("#000000"))
        }

        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> min(desiredWidth, widthSize)
            else -> desiredWidth
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> min(desiredHeight, heightSize)
            else -> heightSize
        }

        setMeasuredDimension(width, height)
    }
}
