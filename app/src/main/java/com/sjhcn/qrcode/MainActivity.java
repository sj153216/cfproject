package com.sjhcn.qrcode;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.sjhcn.myfragment.FirstFragment;
import com.sjhcn.myfragment.FourthFragment;
import com.sjhcn.myfragment.SecondFragment;
import com.sjhcn.myfragment.ThirdFragment;
import com.sjhcn.view.ChangeIconWithText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;

    private ChangeIconWithText mFirstTab;
    private ChangeIconWithText mSecondTab;
    private ChangeIconWithText mThirdTab;
    private ChangeIconWithText mFourthTab;
    private List<ChangeIconWithText> mTabs = new ArrayList<ChangeIconWithText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mFirstTab = (ChangeIconWithText) findViewById(R.id.myQRcode);
        mTabs.add(mFirstTab);
        mSecondTab = (ChangeIconWithText) findViewById(R.id.make);
        mTabs.add(mSecondTab);
        mThirdTab = (ChangeIconWithText) findViewById(R.id.self_center);
        mTabs.add(mThirdTab);
        mFourthTab = (ChangeIconWithText) findViewById(R.id.set);
        mTabs.add(mFourthTab);
    }

    private void initData() {
        Fragment firstFragment = new FirstFragment();
        mFragments.add(firstFragment);
        Fragment secondFragment = new SecondFragment();
        mFragments.add(secondFragment);
        Fragment thirdFragment = new ThirdFragment();
        mFragments.add(thirdFragment);
        Fragment fourthFragment = new FourthFragment();
        mFragments.add(fourthFragment);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        mViewPager.setAdapter(mAdapter);

        mViewPager.setCurrentItem(0);
        resetOthers();
        mTabs.get(0).setAlpha(1.0f);
    }

    private void initEvent() {
        mFirstTab.setOnClickListener(this);
        mSecondTab.setOnClickListener(this);
        mThirdTab.setOnClickListener(this);
        mFourthTab.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    ChangeIconWithText left = mTabs.get(position);
                    ChangeIconWithText right = mTabs.get(position + 1);
                    left.setAlpha(1 - positionOffset);
                    right.setAlpha(positionOffset);

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        resetOthers();
        switch (v.getId()) {
            case R.id.myQRcode:
                mViewPager.setCurrentItem(0);
                mTabs.get(0).setAlpha(1.0f);
                break;
            case R.id.make:
                mViewPager.setCurrentItem(1);
                mTabs.get(1).setAlpha(1.0f);
                break;
            case R.id.self_center:
                mViewPager.setCurrentItem(2);
                mTabs.get(2).setAlpha(1.0f);
                break;
            case R.id.set:
                mViewPager.setCurrentItem(3);
                mTabs.get(3).setAlpha(1.0f);
                break;
        }

    }

    /**
     * 重置其他tab的颜色
     */
    private void resetOthers() {
        for (int i = 0; i < mTabs.size(); i++) {
            mTabs.get(i).setAlpha(0.0f);
        }
    }
}
