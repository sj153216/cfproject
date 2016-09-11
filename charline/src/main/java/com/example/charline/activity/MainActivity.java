package com.example.charline.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.charline.R;
import com.example.charline.adapter.MyPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    private ViewPager mViewPager;
    private MyPagerAdapter mAdapter;
    private ArrayList<View> mViews = new ArrayList<View>();
    private RelativeLayout mTweenTyFourRl;
    private RelativeLayout mFifteenRl;

    private TextView mFifteenTv;
    private TextView mTweentyTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mFifteenTv = (TextView) findViewById(R.id.id_fifteen);
        mTweentyTv = (TextView) findViewById(R.id.id_tweenty);
    }

    private void initData() {
        LayoutInflater inflate = LayoutInflater.from(this);
        mTweenTyFourRl = (RelativeLayout) inflate.inflate(R.layout.activity_tweenty, null);
        mFifteenRl = (RelativeLayout) inflate.inflate(R.layout.activity_fifteen, null);
        mViews.add(mTweenTyFourRl);
        mViews.add(mFifteenRl);
        mAdapter = new MyPagerAdapter(mViews);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
        mTweentyTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_selected));
    }

    private void initEvent() {
        mTweentyTv.setOnClickListener(this);
        mFifteenTv.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mTweentyTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_selected));
                        mFifteenTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_un_selected));
                        break;
                    case 1:
                        mFifteenTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_selected));
                        mTweentyTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_un_selected));
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tweenty:
                mTweentyTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_selected));
                mFifteenTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_un_selected));
                mViewPager.setCurrentItem(0);
                break;
            case R.id.id_fifteen:
                mFifteenTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_selected));
                mTweentyTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_un_selected));
                mViewPager.setCurrentItem(1);
                break;
        }

    }
}
