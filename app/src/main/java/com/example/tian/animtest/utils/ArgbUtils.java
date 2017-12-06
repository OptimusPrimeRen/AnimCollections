package com.example.tian.animtest.utils;

/**
 * Created by Tian on 2017/11/2.
 */

public class ArgbUtils {

    public static Integer evaluate(float fraction, int startValue, int endValue) {

        float startA = (startValue >> 24) & 0xff;
        float startR = (startValue >> 16) & 0xff;
        float startG = (startValue >> 8) & 0xff;
        float startB = (startValue) & 0xff;

        float endA = (endValue >> 24) & 0xff;
        float endR = (endValue >> 16) & 0xff;
        float endG = (endValue >> 8) & 0xff;
        float endB = (endValue) & 0xff;

        float a = startA + fraction * (endA - startA);
        float r = startR + fraction * (endR - startR);
        float g = startG + fraction * (endG - startG);
        float b = startB + fraction * (endB - startB);

        return Math.round(a) << 24 | Math.round(r) << 16 | Math.round(g) << 8 | Math.round(b);
    }
}
