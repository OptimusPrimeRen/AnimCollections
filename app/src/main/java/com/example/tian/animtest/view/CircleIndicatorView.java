package com.example.tian.animtest.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Tian on 17/3/10.
 */

public class CircleIndicatorView extends View {
    private int mPageNum; //总页数
    private int mCurrentPage = 0; //当前的页面，从0开始，与viewpager同步

    private float mDotRadius = 100; //小圆点的半径
    private float mDotMargin = 80; //小圆点之间的间距

    private Paint mPaint;
    private Paint mCirclePaint;
    private Paint mPaintClear;
    private Path mPath;

    private float mAnchroX;
    private float mAnchroY;

    private float mViewWidth;
    private float mViewHeight;

    private ValueAnimator mValueAnimator;

    public CircleIndicatorView(Context context) {
        super(context);
        init();
    }

    public CircleIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true); //抗锯齿
        mPaint.setDither(true); //防抖动
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3); //笔宽
        mPaint.setStyle(Paint.Style.FILL);


        mPath = new Path();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        mAnchroX = mViewWidth / 2;
//        startAnim();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mDotRadius, mDotRadius, mDotRadius, mPaint);
        canvas.drawCircle(mDotRadius * 3 + mDotMargin, mDotRadius, mDotRadius, mPaint);
        canvas.drawCircle(mDotRadius * 5 + mDotMargin * 2, mDotRadius, mDotRadius, mPaint);

        mPath.reset();
        mPath.moveTo((mCurrentPage * 2 + 1) * mDotRadius + mDotMargin * mCurrentPage, 0);
        mPath.quadTo(mAnchroX, mAnchroY, (mCurrentPage * 2 + 3) * mDotRadius + mDotMargin * (mCurrentPage+1), 0);
        mPath.lineTo((mCurrentPage * 2 + 3) * mDotRadius + mDotMargin * (mCurrentPage+1), mViewHeight);
        mPath.quadTo(mAnchroX, mDotRadius * 2 - mAnchroY, (mCurrentPage * 2 + 1) * mDotRadius + mDotMargin * mCurrentPage, mViewHeight);
//        mPath.lineTo(0, 0);
        mPath.close();

        canvas.drawPath(mPath, mPaint);

    }

    private void clearCanvas(Canvas canvas) {
        if (mPaintClear == null) {
            mPaintClear = new Paint();
        }
        mPaintClear.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(mPaintClear);
        mPaintClear.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }

//    private void startAnim() {
//        if (mValueAnimator != null) {
//            return;
//        }
//
//        mValueAnimator = ValueAnimator.ofFloat(0, 1000);
//        mValueAnimator.setDuration(10000);
//        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
////                mAnchroX = (float) animation.getAnimatedValue();
//                mAnchroY = (float) animation.getAnimatedValue();
//                invalidate();
//            }
//        });
//        mValueAnimator.start();
//    }

    public void setViewPager(ViewPager viewPager, int pageNum) {
        mPageNum = pageNum;
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mCurrentPage = position;
            mAnchroX = (mCurrentPage * 2 + 2) * mDotRadius + mDotMargin * mCurrentPage + mDotMargin / 2;
            if (positionOffset <= 0.5) {
                mAnchroY = (1 - positionOffset * 2) * 500;
            } else {
                mAnchroY = positionOffset * 2 * 500;
            }
            invalidate();
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
