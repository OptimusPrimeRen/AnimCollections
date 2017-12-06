package com.example.tian.animtest.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Tian on 17/10/16.
 */

public class NotFocusViewPager extends ViewPager {
    public NotFocusViewPager(Context context) {
        super(context);
    }

    public NotFocusViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return super.onInterceptTouchEvent(ev);
    }
}
