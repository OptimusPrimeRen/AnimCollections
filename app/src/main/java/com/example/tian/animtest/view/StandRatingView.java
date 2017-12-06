package com.example.tian.animtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.tian.animtest.R;
import com.example.tian.animtest.bean.SuccessRatingBean;
import com.example.tian.animtest.utils.DrawUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tian on 17/9/11.
 * 运势评分卡
 */

public class StandRatingView extends View {
    private static final int HEIGHT_UNIT = DrawUtils.dip2px(100); //进度条为1.0时对应的view的基准高度

    private static final int RATING_TEXT_SIZE = DrawUtils.dip2px(14); //分数字体的大小
    private static final int TITLE_TEXT_SIZE = DrawUtils.dip2px(14); //底部标题字体的大小
    private static final int RATING_TEXT_MARGIN_BAR = DrawUtils.dip2px(10); //分数距离评分条的高度
    private static final int TITLE_MARGIN_BAR = DrawUtils.dip2px(12); //底部标题距离评分条高度
    private static final int RATING_BAR_WIDTH = DrawUtils.dip2px(10); //评分条宽度

    private List<DataDecorator> mDataDecorators = new ArrayList<>();

    private float mWidth;
    private float mViewHeight; //整个视图的高度，由最长的rating bar加文字高度及边距高度组成

    private Paint mRatingBarPaint;
    private Paint mTextPaint;

    public StandRatingView(Context context) {
        super(context);
        init();
    }

    public StandRatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StandRatingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mRatingBarPaint = new Paint();
        mRatingBarPaint.setStrokeCap(Paint.Cap.ROUND);
        mRatingBarPaint.setStrokeWidth(RATING_BAR_WIDTH);
        mRatingBarPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, (int) mViewHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float ratingBarCenterX; //每个单元的x轴中心
        DataDecorator decorator;
        for (int i = 0; i < mDataDecorators.size(); i++) {
            decorator = mDataDecorators.get(i);
            ratingBarCenterX = mWidth / 12 + mWidth / 6 * i;

            //画评分条
            mRatingBarPaint.setShader(mDataDecorators.get(i).mLinearGradient);
            canvas.drawLine(ratingBarCenterX, decorator.mRatingBarStartY, ratingBarCenterX, decorator.mRatingBarEndY, mRatingBarPaint);

            //画底部标题
            mTextPaint.setTextSize(TITLE_TEXT_SIZE);
            mTextPaint.setTypeface(Typeface.DEFAULT);
            canvas.drawText(decorator.getName(), ratingBarCenterX, mViewHeight, mTextPaint);

            //画评分数字
            mTextPaint.setTextSize(RATING_TEXT_SIZE);
            mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
            canvas.drawText(Float.toString(decorator.getRating()), ratingBarCenterX, decorator.mRatingBarStartY - RATING_TEXT_MARGIN_BAR, mTextPaint);
        }
    }

    /**
     * 设置数据 解析转换后绘制
     *
     * @param successRatingBeen
     */
    public void setData(List<SuccessRatingBean> successRatingBeen) {
        successRatingBeen = new ArrayList<>();
        successRatingBeen.add(new SuccessRatingBean("Lover", 2.0f, getResources().getColor(R.color.success_rating_lover_top_color), getResources().getColor(R.color.success_rating_lover_bottom_color)));
        successRatingBeen.add(new SuccessRatingBean("Healthy", 1.0f, getResources().getColor(R.color.success_rating_healthy_top_color), getResources().getColor(R.color.success_rating_healthy_bottom_color)));
        successRatingBeen.add(new SuccessRatingBean("Wealth", 1.8f, getResources().getColor(R.color.success_rating_wealth_top_color), getResources().getColor(R.color.success_rating_wealth_bottom_color)));
        successRatingBeen.add(new SuccessRatingBean("Family", 0.8f, getResources().getColor(R.color.success_rating_family_top_color), getResources().getColor(R.color.success_rating_family_bottom_color)));
        successRatingBeen.add(new SuccessRatingBean("Career", 2.2f, getResources().getColor(R.color.success_rating_career_top_color), getResources().getColor(R.color.success_rating_career_bottom_color)));
        successRatingBeen.add(new SuccessRatingBean("Manager", 0.6f, getResources().getColor(R.color.success_rating_marriage_top_color), getResources().getColor(R.color.success_rating_marriage_bottom_color)));

        float ratingBarMaxHeight = 0;
        for (SuccessRatingBean bean : successRatingBeen) {
            ratingBarMaxHeight = bean.getRating() > ratingBarMaxHeight ? bean.getRating() : ratingBarMaxHeight;
        }
        mViewHeight = ratingBarMaxHeight * HEIGHT_UNIT + RATING_TEXT_SIZE + RATING_TEXT_MARGIN_BAR + TITLE_MARGIN_BAR + TITLE_TEXT_SIZE;

        for (SuccessRatingBean bean : successRatingBeen) {
            mDataDecorators.add(new DataDecorator(bean, mViewHeight));
        }

        requestLayout();


    }

    /**
     * 数据包装类
     * 使用传入的数据  计算出  view绘制需要的坐标及色彩渐变区域
     */
    private class DataDecorator extends SuccessRatingBean {
        float mRatingBarStartY;
        float mRatingBarEndY;
        LinearGradient mLinearGradient;

        DataDecorator(SuccessRatingBean bean, float viewHeight) {
            super(bean.getName(), bean.getRating(), bean.getTopColor(), bean.getBottomColor());
            mRatingBarStartY = viewHeight - bean.getRating() * HEIGHT_UNIT;
            mRatingBarEndY = viewHeight - TITLE_TEXT_SIZE - TITLE_MARGIN_BAR;
            mLinearGradient = new LinearGradient(0, mRatingBarStartY, 0, mRatingBarEndY, bean.getTopColor(), bean.getBottomColor(), Shader.TileMode.CLAMP);
        }
    }

}
