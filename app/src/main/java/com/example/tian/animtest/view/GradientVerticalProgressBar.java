package com.example.tian.animtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Tian on 17/9/11.
 */

public class GradientVerticalProgressBar extends View {
    private static final int HEIGHT_UNIT = 100; //进度条为1.0时对应的view的基准高度

    @ColorInt
    private int mTopColor = Color.BLUE;

    @ColorInt
    private int mBottomColor = Color.RED;

    private float mHeight;
    private float mWidth;

    private Paint mPaint;

    public GradientVerticalProgressBar(Context context) {
        super(context);
        init();
    }

    public GradientVerticalProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GradientVerticalProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, (int) mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w == 0 || h == 0) {
            return;
        }
        mWidth = w;
        LinearGradient linearGradient = new LinearGradient(0, 0, 0, mHeight, mTopColor, mBottomColor, Shader.TileMode.MIRROR);
        mPaint.setShader(linearGradient);
        mPaint.setStrokeWidth(mWidth);
//        anim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(mWidth / 2, mWidth / 2, mWidth / 2, mHeight - mWidth / 2, mPaint);
    }

//    private void anim() {
//        if (mWidth == 0 && mHeight == 0 && mAnimHeight != mHeight) {
//
//            ValueAnimator animator = ValueAnimator.ofFloat(mWidth / 2, mHeight - mWidth / 2);
//            animator.setDuration((long) (mHeight - mAnimHeight) / HEIGHT_UNIT);
//            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    mAnimHeight = (float) animation.getAnimatedValue();
//                    invalidate();
//                }
//            });
//            animator.start();
//        }
//    }

    public void setProgress(float progress) {
        mHeight = HEIGHT_UNIT * progress;
        requestLayout();


    }

    public void setColor(@ColorInt int topColor, @ColorInt int bottomColor) {
        mTopColor = topColor;
        mBottomColor = bottomColor;
    }

}
