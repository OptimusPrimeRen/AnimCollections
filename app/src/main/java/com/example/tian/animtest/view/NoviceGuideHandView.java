package com.example.tian.animtest.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.tian.animtest.R;

/**
 * Created by Tian on 17/3/8.
 * 新手引导的手  视图
 */

public class NoviceGuideHandView extends View {

    private static final float HAND_INIT_SCALE = 1.5f;

    private AnimFinishListener mAnimFinishListener;

    private Bitmap mHandBitmap;
    private Bitmap mHandMaskBitmap;
    private Paint mMaskPaint;
    private Paint mHandPaint;

    private float mViewHeight;
    private float mViewWidth;
    private float mHandX;
    private float mHandY;
    private float mHandScale = HAND_INIT_SCALE;

    public NoviceGuideHandView(Context context) {
        super(context);
        init();
    }

    public NoviceGuideHandView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NoviceGuideHandView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mHandBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.novice_guide_hand);
        mHandMaskBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.novice_guide_shadow);
        mHandPaint = new Paint();
        mMaskPaint = new Paint();
        mHandPaint.setAntiAlias(true);
        mMaskPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        startHandAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.saveLayer(0, 0, getWidth(), getHeight(), null,
                Canvas.MATRIX_SAVE_FLAG |
                        Canvas.CLIP_SAVE_FLAG |
                        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                        Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                        Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.save();
        canvas.scale(mHandScale, mHandScale, 0, 0);
        canvas.drawBitmap(mHandBitmap, mHandX, mHandY, mHandPaint);
        canvas.restore();

        mMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(mHandMaskBitmap, 0, 0, mMaskPaint);
        mMaskPaint.setXfermode(null);

//        canvas.restore();
    }

    private void startHandAnim() {
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(getHandTranslationAnim(), getHandScaleAnim());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mAnimFinishListener != null) {
                    mAnimFinishListener.onAnimFinish();
                }
            }
        });
        set.start();
    }

    /**
     * 手指的移动动画
     */
    private Animator getHandTranslationAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(mViewWidth, mViewWidth - mHandBitmap.getWidth());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mHandX = (float) animation.getAnimatedValue() * 0.9f;
                mHandY = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(2000);
        return animator;
    }

    /**
     * 手指的缩放动画
     */
    private Animator getHandScaleAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(HAND_INIT_SCALE, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mHandScale = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(2000);
        return animator;
    }

    public interface AnimFinishListener {
        void onAnimFinish();
    }

    public void setAnimFinishListener(AnimFinishListener animFinishListener) {
        mAnimFinishListener = animFinishListener;
    }
}
