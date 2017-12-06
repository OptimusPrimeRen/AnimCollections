package com.example.tian.animtest;

import android.os.Bundle;
import android.support.transition.ChangeBounds;
import android.support.transition.Scene;
import android.support.transition.TransitionManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tian.animtest.bean.SuccessRatingBean;
import com.example.tian.animtest.utils.DrawUtils;
import com.example.tian.animtest.view.AutoTransitionView;
import com.example.tian.animtest.view.BezierCurveLoadingView;
import com.example.tian.animtest.view.CircleRatingView;
import com.example.tian.animtest.view.StandRatingView;
import com.example.tian.animtest.view.TabLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<View> viewList = new ArrayList<>(5);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawUtils.init(getApplicationContext());
        setContentView(R.layout.activity_main);
        View view1 = LayoutInflater.from(this).inflate(R.layout.card_locker_screen_horo,null,false);
        View view2 = LayoutInflater.from(this).inflate(R.layout.card_locker_screen_horo,null,false);
        View view3 = LayoutInflater.from(this).inflate(R.layout.card_locker_screen_horo,null,false);
        View view4 = LayoutInflater.from(this).inflate(R.layout.card_locker_screen_horo,null,false);
        View view5 = LayoutInflater.from(this).inflate(R.layout.card_locker_screen_horo,null,false);

        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewList.add(view5);
        ViewPager viewPager = findViewById(R.id.view_pager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        List<String> titleList = new ArrayList<>(5);
        titleList.add("1");
        titleList.add("2");
        titleList.add("3");
        titleList.add("4");
        titleList.add("5");
        tabLayout.initTitles(titleList);
        tabLayout.attachToViewPager(viewPager);
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
