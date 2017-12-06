package com.example.tian.animtest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tian.animtest.R;
import com.example.tian.animtest.utils.ArgbUtils;

import java.util.List;

/**
 * Created by Tian on 2017/9/11.
 */
@ViewPager.DecorView
public class TabLayout extends LinearLayout {
    private float mIndicatorWidth = 36;
    private float mIndicatorHeight = 3;
    private int mIndicatorColor = 0xffffffff;
    private float mDividerHeight = 3;
    private int mDividerColor = 0x4cffffff;
    private int mNormalColor = 0x4cffffff;
    private int mSelectColor = 0xffffd803;
    private float mTextSize = 13;
    private int mMaxVisiableTabCount = 4;

    private float mStartOffset;

    private ViewPager mViewPager;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private ViewPager.OnPageChangeListener mListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mStartOffset = getChildWidth() * (position + positionOffset);
            for (int i = 0; i < getChildCount(); i++) {
                TextView child = (TextView) getChildAt(i);
                if (position == i) {
                    child.setTextColor(ArgbUtils.evaluate(positionOffset, mSelectColor, mNormalColor));
                } else if (position + 1 == i) {
                    child.setTextColor(ArgbUtils.evaluate(positionOffset, mNormalColor, mSelectColor));
                } else {
                    child.setTextColor(mNormalColor);
                }
            }
            TabLayout.this.scrollTo((int) (getChildWidth() * positionOffset),0);
            invalidate();
        }
    };

    public TabLayout(Context context) {
        super(context);
    }

    public TabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray attribute = context.obtainStyledAttributes(attrs, R.styleable.TabLayout);
        mIndicatorWidth = attribute.getDimension(R.styleable.TabLayout_indicatorWidth, mIndicatorWidth);
        mIndicatorHeight = attribute.getDimension(R.styleable.TabLayout_indicatorHeight, mIndicatorHeight);
        mIndicatorColor = attribute.getColor(R.styleable.TabLayout_indicatorColor, mIndicatorColor);
        mDividerHeight = attribute.getDimension(R.styleable.TabLayout_dividerHeight, mDividerHeight);
        mDividerColor = attribute.getColor(R.styleable.TabLayout_dividerColor, mDividerColor);
        mNormalColor = attribute.getColor(R.styleable.TabLayout_normalColor, mNormalColor);
        mSelectColor = attribute.getColor(R.styleable.TabLayout_selectColor, mSelectColor);
        mTextSize = attribute.getDimension(R.styleable.TabLayout_textSize, mTextSize);
        attribute.recycle();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mPaint.setColor(mDividerColor);
        canvas.drawRect(0, getHeight() - mDividerHeight, getWidth(), getHeight(), mPaint);
        mPaint.setColor(mIndicatorColor);
        float center = getChildWidth() / 2 + mStartOffset;
        canvas.drawRect(center - mIndicatorWidth / 2, getHeight() - mIndicatorHeight,
                center + mIndicatorWidth / 2, getHeight(), mPaint);
    }

    private int getChildWidth() {
        if (getChildCount() == 0) {
            return getWidth();
        }
        View child = getChildAt(0);
        return child.getWidth();
    }

    public void initTitles(@NonNull List<String> titleList) {
        TextView titleText;
        LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.width = 1080/mMaxVisiableTabCount;
        params.bottomMargin = (int) mDividerHeight;
        String title;
        for (int i = 0; i < titleList.size(); i++) {
            title = titleList.get(i);
            titleText = new TextView(getContext());
            titleText.setTextColor(mNormalColor);
            titleText.setTextSize(mTextSize);
            titleText.setText(title);
            titleText.setGravity(Gravity.CENTER);
            titleText.setTag(i);
            titleText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mViewPager != null) {
                        mViewPager.setCurrentItem((int) view.getTag(), true);
                    }
                }
            });
            addView(titleText, params);
        }
    }

    public void attachToViewPager(@NonNull ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(mListener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mViewPager != null) {
            mViewPager.removeOnPageChangeListener(mListener);
            for (int i = 0; i < getChildCount(); i++) {
                getChildAt(i).setOnClickListener(null);
            }
        }
    }
}
