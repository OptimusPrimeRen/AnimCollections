package com.example.tian.animtest.bean;

import android.support.annotation.ColorInt;

/**
 * Created by Tian on 17/9/11.
 */

public class SuccessRatingBean {

    private String mName;
    private float mRating;

    @ColorInt
    private int mTopColor;
    private int mBottomColor;

    public SuccessRatingBean(String name, float rating, int topColor, int bottomColor) {
        mName = name;
        mRating = rating;
        mTopColor = topColor;
        mBottomColor = bottomColor;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        mRating = rating;
    }

    public int getTopColor() {
        return mTopColor;
    }

    public void setTopColor(int topColor) {
        mTopColor = topColor;
    }

    public int getBottomColor() {
        return mBottomColor;
    }

    public void setBottomColor(int bottomColor) {
        mBottomColor = bottomColor;
    }
}
