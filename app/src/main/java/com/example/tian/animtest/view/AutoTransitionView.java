package com.example.tian.animtest.view;

import android.content.Context;
import android.support.transition.Scene;
import android.support.transition.TransitionManager;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tian.animtest.R;

/**
 * Created by Tian on 17/9/20.
 */

public class AutoTransitionView extends RelativeLayout {
    private ImageView mIvHoroIcon;
    private TextView mTvHoroTitle;
    private TextView mTvRatingTitle;
    private RatingBar mRbRating;
    private TextView mTvContent;

    public AutoTransitionView(Context context) {
        super(context);
    }

    public AutoTransitionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoTransitionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mIvHoroIcon = (ImageView) findViewById(R.id.iv_locker_horo_icon);
        mTvHoroTitle = (TextView) findViewById(R.id.tv_locker_horo_name);
        mTvRatingTitle = (TextView) findViewById(R.id.tv_locker_fortune_rating);
        mRbRating = (RatingBar) findViewById(R.id.rb_locker_fortune_rating);
        mTvContent = (TextView) findViewById(R.id.tv_locker_fortune_detail);
    }

//    void refreshData(LockerScreenFortuneCard card) {
//        mIvHoroIcon.setImageResource(HoroscopeResUtil.getHoroscopeMinIcon(card.getHoroscope().getId()));
//        mTvHoroTitle.setText(card.getHoroscope().getName());
//        mTvRatingTitle.setText("Taday`s " + card.getTitleByCategory() + " Success Rating");
//        mRbRating.setRating(card.getRating());
//        mTvContent.setText(card.getTips());
//        setVisibility(VISIBLE);
//    }

    /**
     * 缩小卡片
     */
    public void shrinkDownCard() {
//        this.animate().translationY(DrawUtils.dip2px(-100));
//        Scene scene1 = Scene.getSceneForLayout(this, R.layout.card_locker_screen_horo, getContext());
        Scene scene2 = Scene.getSceneForLayout(this, R.layout.card_locker_screen_horo_scene_2, getContext());
        TransitionManager.go(scene2);
//        TransitionManager.beginDelayedTransition(new Scene(this, R.layout.card_locker_screen_horo));
    }

    boolean isShow() {
        return getVisibility() == VISIBLE;
    }

}