package com.example.tian.animtest.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.tian.animtest.R;
import com.example.tian.animtest.utils.DrawUtils;

/**
 * Created by Tian on 17/3/10.
 * Loading动画  （演示白塞尔曲线的使用）
 */

public class BezierCurveLoadingView extends View {
    private static final int DEFAULT_CIRCLE_COLOR = Color.GREEN;
    private static final int DEFAULT_CYCLE_TIME = 500;
    private static final int DEFAULT_ROTATE_DEGREES_OFFSET = 3;

    private float mCircleInitRadius; //圆初始化大小
    private int mCircleColor; //颜色
    private int mCycleTime; //变换周期时间
    private int mDegreesOffset; //旋转角度增量
    private float mAnchorYOffset; //贝塞尔锚点Y轴偏移量
    private float mViewWidth;
    private float mViewHeight;

    private Paint mPaint;
    private Path mPath;

    private float mDotRadius; //小圆点的半径
    private float mDrawingCoefficient; //拉伸系数
    private float mCircleOneCenterX; //第一个圆的圆心x
    private float mCircleOneCenterY; //第一个圆的圆心y
    private float mDegrees; //旋转角度

    private ValueAnimator mValueAnimator;

    public BezierCurveLoadingView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public BezierCurveLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public BezierCurveLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.BezierCurveLoadingView, defStyleAttr, 0);
        mCircleInitRadius = DrawUtils.dip2px(a.getDimension(R.styleable.BezierCurveLoadingView_circleSize, 0));
        mCircleColor = a.getColor(R.styleable.BezierCurveLoadingView_circleColor, DEFAULT_CIRCLE_COLOR);
        mCycleTime = a.getInt(R.styleable.BezierCurveLoadingView_cycleTime, DEFAULT_CYCLE_TIME);
        mDegreesOffset = a.getInt(R.styleable.BezierCurveLoadingView_rotateDegreesOffset, DEFAULT_ROTATE_DEGREES_OFFSET);
        a.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true); //抗锯齿
        mPaint.setDither(true); //防抖动
        mPaint.setColor(mCircleColor);
        mPaint.setStrokeWidth(3); //笔宽
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;

        mCircleOneCenterY = mViewHeight / 2;
        if (mCircleInitRadius <= 0) {
            mCircleInitRadius = mViewHeight / 3;
        }
        mAnchorYOffset = mCircleInitRadius * 0.1f;
        startLoading();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.rotate(mDegrees, mViewWidth / 2, mViewHeight / 2);

        canvas.drawCircle(mCircleOneCenterX, mCircleOneCenterY, mDotRadius, mPaint);
        canvas.drawCircle(mViewWidth - mCircleOneCenterX, mCircleOneCenterY, mDotRadius, mPaint);

        mPath.reset();
        mPath.moveTo(mCircleOneCenterX, mCircleOneCenterY - mDotRadius);
        mPath.quadTo(mViewWidth / 2, mCircleOneCenterY - mDotRadius + mAnchorYOffset, mViewWidth - mCircleOneCenterX, mCircleOneCenterY - mDotRadius);
        mPath.lineTo(mViewWidth - mCircleOneCenterX, mCircleOneCenterY + mDotRadius);
        mPath.quadTo(mViewWidth / 2, mCircleOneCenterY + mDotRadius - mAnchorYOffset, mCircleOneCenterX, mCircleOneCenterY + mDotRadius);
        mPath.lineTo(mCircleOneCenterX, mCircleOneCenterY - mDotRadius);
        mPath.close();

        canvas.drawPath(mPath, mPaint);
    }

    private void startLoading() {
        mValueAnimator = ValueAnimator.ofFloat(0.2f, 1.0f);
        mValueAnimator.setDuration(mCycleTime);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDrawingCoefficient = (float) animation.getAnimatedValue();
                mDotRadius = mCircleInitRadius * mDrawingCoefficient;
                mCircleOneCenterX = mViewWidth / 2 * mDrawingCoefficient;
                mDegrees = mDegrees >= 360 ? mDegrees + mDegreesOffset - 360 : mDegrees + mDegreesOffset;
                invalidate();
            }
        });
        mValueAnimator.start();
    }

    private void stopLoading() {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopLoading();
    }
}
