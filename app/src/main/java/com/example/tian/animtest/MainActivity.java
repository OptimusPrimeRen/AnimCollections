package com.example.tian.animtest;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tian.animtest.utils.DensityUtil;
import com.example.tian.animtest.view.BezierCurveLoadingView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BezierCurveLoadingView mBezierCurveLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DensityUtil.init(getApplicationContext());
        setContentView(R.layout.activity_main);
        mBezierCurveLoadingView = (BezierCurveLoadingView) findViewById(R.id.circle_indicator);
    }
}
