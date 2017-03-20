package com.example.tian.animtest;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.example.tian.animtest.utils.DensityUtil;
import com.example.tian.animtest.view.CircleIndicatorView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private CircleIndicatorView mCircleIndicatorView;

    private List<View> mViews = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DensityUtil.init(getApplicationContext());
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mCircleIndicatorView = (CircleIndicatorView) findViewById(R.id.circle_indicator);
        initPages();
        mViewPager.setAdapter(new NoviceGuideAdapter());
        mCircleIndicatorView.setViewPager(mViewPager,3);

    }

    private void initPages() {
        LayoutInflater inflater = getLayoutInflater();
        View page1 = inflater.inflate(R.layout.page_1, null);
        View page2 = inflater.inflate(R.layout.page_1, null);
        View page3 = inflater.inflate(R.layout.page_1, null);
        mViews.add(page1);
        mViews.add(page2);
        mViews.add(page3);

        TextView textView1 = (TextView) page1.findViewById(R.id.page_num);
        textView1.setText("1");
        TextView textView2 = (TextView) page2.findViewById(R.id.page_num);
        textView2.setText("2");
        TextView textView3 = (TextView) page3.findViewById(R.id.page_num);
        textView3.setText("3");
    }

    /**
     * viewpager 适配器
     */
    private class NoviceGuideAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViews.get(position), 0);
            return mViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position));
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
