package com.example.charline.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by sjhcn on 2016/9/4.
 */
public class MyPagerAdapter extends PagerAdapter {
    private ArrayList<View> mViewList;

    public MyPagerAdapter(ArrayList<View> mViewList) {
        this.mViewList = mViewList;
    }

    @Override
    public int getCount() {
        return mViewList.size();// 页卡数
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;// 官方推荐写法
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));// 添加页卡
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));// 删除页卡
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";// 页卡标题
    }

}