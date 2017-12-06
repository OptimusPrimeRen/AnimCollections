package com.example.tian.animtest.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.tian.animtest.R;
import com.example.tian.animtest.utils.DrawUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tian on 17/9/11.
 * 环装运势评分卡 暂用于星座匹配结果页
 */

public class CircleRatingView extends View {
    private static final int RATING_BAR_WIDTH = DrawUtils.dip2px(10);
    private static final int RATING_BAR_BG_WIDTH = DrawUtils.dip2px(5);
    private static final int RATING_BAR_RADIUS = DrawUtils.dip2px(25);

    private static final int NUMBER_TEXT_SIZE = DrawUtils.dip2px(16); //数字字体大小
    private static final int TITLE_TEXT_SIZE = DrawUtils.dip2px(14); //底部标题字体的大小

    public static final int RATING_BAR_MATGIN_TOP = DrawUtils.dip2px(20);
    private static final int TITLE_MARGIN_BAR = DrawUtils.dip2px(12); //底部标题距离评分条高度
    public static final int TITLE_MATGIN_BOTTOM = DrawUtils.dip2px(10);

    @ColorInt
    public static final int RATING_BAR_BG_COLOR = 0x88ffffff;


    private List<RatingCardWrapper> mRatingCardWrappers = new ArrayList<>();

    private Paint mRatingBarPaint;
    private Paint mRatingBarBgPaint;
    private Paint mTextPaint;

    public CircleRatingView(Context context) {
        super(context);
        init();
    }

    public CircleRatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleRatingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mRatingBarPaint = new Paint();
        mRatingBarPaint.setStrokeCap(Paint.Cap.ROUND);
        mRatingBarPaint.setStyle(Paint.Style.STROKE);
        mRatingBarPaint.setStrokeWidth(RATING_BAR_WIDTH);
        mRatingBarPaint.setAntiAlias(true);

        mRatingBarBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRatingBarBgPaint.setStrokeCap(Paint.Cap.ROUND);
        mRatingBarBgPaint.setStyle(Paint.Style.STROKE);
        mRatingBarBgPaint.setColor(RATING_BAR_BG_COLOR);
        mRatingBarBgPaint.setStrokeWidth(RATING_BAR_BG_WIDTH);

        mTextPaint = new Paint();
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, RATING_BAR_RADIUS * 2 + TITLE_TEXT_SIZE + TITLE_MARGIN_BAR + RATING_BAR_MATGIN_TOP + TITLE_MATGIN_BOTTOM);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        for (int i = 0, size = mRatingCardWrappers.size(); i < size; i++) {
            mRatingCardWrappers.get(i).calculateDrawPara(i, w, h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RatingCardWrapper bean;
        for (int i = 0, size = mRatingCardWrappers.size(); i < size; i++) {
            bean = mRatingCardWrappers.get(i);

            //画评分条
            canvas.save();
            mRatingBarPaint.setShader(mRatingCardWrappers.get(i).mSweepGradient);
            canvas.rotate(90, bean.mCenterX, bean.mCenterY);
            canvas.drawArc(bean.mRatingBarRectF, 320 * bean.mRating + 20, 320 - bean.mRating * 320, false, mRatingBarBgPaint);
            canvas.drawArc(bean.mRatingBarRectF, 20, 320 * bean.mRating, false, mRatingBarPaint);
            canvas.restore();

            //画底部标题
            mTextPaint.setTextSize(TITLE_TEXT_SIZE);
            mTextPaint.setTypeface(Typeface.DEFAULT);
            canvas.drawText(bean.mName, bean.mCenterX, getHeight() - TITLE_MATGIN_BOTTOM, mTextPaint);

            //画评分数字
            mTextPaint.setTextSize(NUMBER_TEXT_SIZE);
            mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText(Integer.toString(Math.round(bean.mRating * 100)), bean.mCenterX - DrawUtils.dip2px(5), bean.mCenterY + NUMBER_TEXT_SIZE / 2, mTextPaint);

            //画百分号
            mTextPaint.setTextSize(TITLE_TEXT_SIZE);
            mTextPaint.setTypeface(Typeface.DEFAULT);
            canvas.drawText("%", bean.mCenterX + DrawUtils.dip2px(10), bean.mCenterY + NUMBER_TEXT_SIZE / 2, mTextPaint);
        }
    }

    /**
     * 设置数据 解析转换后绘制
     */
    public void setData() {

        Resources resources = getResources();
        if (mRatingCardWrappers.size() == 3) {
            mRatingCardWrappers.get(0).mRating = 0f;
            mRatingCardWrappers.get(1).mRating = 0.5f;
            mRatingCardWrappers.get(2).mRating = 1f;
        } else {
            mRatingCardWrappers.add(new RatingCardWrapper("Over all",
                    0f,
                    ContextCompat.getColor(getContext(), R.color.success_rating_lover_top_color),
                    ContextCompat.getColor(getContext(), R.color.success_rating_lover_bottom_color)));
            mRatingCardWrappers.add(new RatingCardWrapper("Lover",
                    0.5f,
                    ContextCompat.getColor(getContext(), R.color.success_rating_wealth_top_color),
                    ContextCompat.getColor(getContext(), R.color.success_rating_wealth_bottom_color)));
            mRatingCardWrappers.add(new RatingCardWrapper("Value",
                    1f,
                    ContextCompat.getColor(getContext(), R.color.success_rating_marriage_top_color),
                    ContextCompat.getColor(getContext(), R.color.success_rating_marriage_bottom_color)));
        }

        requestLayout();
    }

    /**
     * 数据包装
     * 使用传入的数据  计算出  view绘制需要的坐标及色彩渐变区域
     */
    private class RatingCardWrapper {
        String mName;

        float mRating;

        @ColorInt
        int mStartColor;

        @ColorInt
        int mEndColor;

        float mCenterX;
        float mCenterY;

        RectF mRatingBarRectF = new RectF();

        SweepGradient mSweepGradient;

        RatingCardWrapper(String name, float rating, int startColor, int endColor) {
            mName = name;
            mRating = rating;
            mStartColor = startColor;
            mEndColor = endColor;
        }

        /**
         * 计算绘制参数
         */
        void calculateDrawPara(int position, float viewWidth, float viewHeight) {
            mCenterX = viewWidth / 6 + viewWidth / 3 * position;
            mCenterY = RATING_BAR_RADIUS + RATING_BAR_MATGIN_TOP;
            mRatingBarRectF.left = mCenterX - RATING_BAR_RADIUS;
            mRatingBarRectF.right = mCenterX + RATING_BAR_RADIUS;
            mRatingBarRectF.top = mCenterY - RATING_BAR_RADIUS;
            mRatingBarRectF.bottom = mCenterY + RATING_BAR_RADIUS;

            int[] colors = new int[]{mStartColor, mEndColor};
            float[] positions = new float[]{0, mRating};
            mSweepGradient = new SweepGradient(mCenterX, mCenterY, colors, positions);
        }
    }

}
