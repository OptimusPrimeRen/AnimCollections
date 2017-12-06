package com.example.tian.animtest.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Tian on 17/8/17.
 * 圆形计时器
 */

public class RoundTimer extends View {
    private int mArcWidth = 2; //弧线宽度unit : dp
    private int mArcColor = Color.RED; //弧线颜色

    private float mOffset = 0; //进度

    private Paint mPaint;
    private RectF mRectF;

    public RoundTimer(Context context) {
        super(context);
        init();
    }

    public RoundTimer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundTimer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mArcColor);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF = new RectF(5, 5, w - 5, h - 5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(mRectF, -90, 360 - mOffset * 360, false, mPaint);
    }

    /**
     * 开始计时
     *
     * @param time 计时的时间长度
     */
    public void start(int time) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(time);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }
}
