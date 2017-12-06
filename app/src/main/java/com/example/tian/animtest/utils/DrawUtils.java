package com.example.tian.animtest.utils;

import android.content.Context;

/**
 * Created by Tian on 17/3/13.
 */

public class DrawUtils {

    private static float sScale;

    public static void init(Context context){
        sScale = context.getResources().getDisplayMetrics().density;

    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        return (int) (dpValue * sScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        return (int) (pxValue / sScale + 0.5f);
    }
}