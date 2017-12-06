package com.example.tian.animtest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.tian.animtest.utils.DrawUtils;

/**
 * Created by TIan on 2017/11/3.
 */

public class PolygonRatingView extends View {

    private static final int NET_COLOR = 0x4cffffff; //六边形网颜色
    private static final int RATING_STROKE_COLOR = 0xff602dfc; //分数边线
    private static final int RATING_AREA_TOP_COLOR = 0xb28d3aff; //分数区域顶部色
    private static final int RATING_AREA_BOTTOM_COLOR = 0xb23710ff; //分数区域底部色
    private static final int TEXT_COLOR = 0xffffffff; //文字颜色

    private static final int LINE_WIDTH = 3;
    private static final int RATING_TEXT_SIZE = 48;
    private static final int TITLE_TEXT_SIZE = 36;

    private static final float[] DASH_LINE = {5, 5}; //虚线
    private static final int COUNT = 6;

    private Paint mSolidPaint; //填充块
    private Paint mDashLinePaint; //虚线
    private Paint mLinePaint; //普通线条
    private Paint mTextPaint; //文字
    private Matrix mMatrix;
    private float mLineLength;
    private float mCenter;
    private int[] mInts = new int[]{5, 1, 5, 4, 5, 3};
    private Path mPath;

    public PolygonRatingView(Context context) {
        super(context);
    }

    public PolygonRatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(NET_COLOR);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(LINE_WIDTH);

        mDashLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDashLinePaint.setStyle(Paint.Style.STROKE);
        mDashLinePaint.setStrokeWidth(LINE_WIDTH);
        mDashLinePaint.setColor(NET_COLOR);
        mDashLinePaint.setPathEffect(new DashPathEffect(DASH_LINE, 0));

        mSolidPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSolidPaint.setStyle(Paint.Style.FILL);
        mSolidPaint.setColor(RATING_STROKE_COLOR);
        mSolidPaint.setStrokeWidth(LINE_WIDTH);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(TEXT_COLOR);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(48);

        mMatrix = new Matrix();
        mPath = new Path();
    }

    private void calculatePoint() {
        mSolidPaint.setShader(new LinearGradient(mCenter, mCenter - mLineLength, mCenter, mCenter + mLineLength,
                RATING_AREA_TOP_COLOR, RATING_AREA_BOTTOM_COLOR, Shader.TileMode.CLAMP));

        float[] src = new float[2];
        src[0] = mCenter;
        float[] des = new float[2];
        mMatrix.reset();
        for (int i = 0; i < COUNT; i++) {
            mMatrix.postRotate(360f / COUNT, mCenter, mCenter);
            src[1] = mCenter - mLineLength * mInts[i] / 5;
            mMatrix.mapPoints(des, src);
            if (i == 0) {
                mPath.moveTo(des[0], des[1]);
            } else {
                mPath.lineTo(des[0], des[1]);
            }
        }
        mPath.close();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = MeasureSpec.getSize(widthMeasureSpec);
        mLineLength = size / 3;
        mCenter = size / 2;
        calculatePoint();
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(LAYER_TYPE_SOFTWARE, null); //为了画虚线，关闭硬件加速
        mLinePaint.setColor(NET_COLOR);
        canvas.save();
        for (int i = 0; i < COUNT; i++) {
            canvas.rotate(360f / COUNT, mCenter, mCenter);
            for (int j = 2; j <= 5; j++) {
                float l = mLineLength * j / 5;
                float x = (float) (mCenter - l * Math.sin(2 * Math.PI / COUNT));
                float y = (float) (mCenter - l * Math.cos(2 * Math.PI / COUNT));
                canvas.drawLine(x, y, mCenter, mCenter - l, mLinePaint); //画网格
            }
            canvas.save();
            float l = (float) (mLineLength / 5.0 * 6.3);
            float x = (float) (mCenter - l * Math.sin(2 * Math.PI / COUNT));
            float y = (float) (mCenter - (l) * Math.cos(2 * Math.PI / COUNT));
            canvas.rotate(-60 * (i + 1), x, y);
            canvas.drawText("0.4", x, y + RATING_TEXT_SIZE / 2 + TITLE_TEXT_SIZE / 2, mTextPaint); //画分数
            canvas.drawText("Career", x, y + RATING_TEXT_SIZE / 2 - TITLE_TEXT_SIZE / 2 - DrawUtils.dip2px(5), mTextPaint); //画标题
            canvas.restore();
            canvas.drawLine(mCenter, (float) (mCenter - mLineLength / 5.0 * 2), mCenter, mCenter - mLineLength, mDashLinePaint); //画虚线
        }
        canvas.restore();
        canvas.drawPath(mPath, mSolidPaint); //画填充色
        mLinePaint.setColor(RATING_STROKE_COLOR);
        canvas.drawPath(mPath, mLinePaint); //画填充块边线
        canvas.drawText("3.0", mCenter, mCenter + RATING_TEXT_SIZE / 2, mTextPaint); //画中央平均分数
    }

}
