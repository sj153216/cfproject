package com.sjhcn.myfragment;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjhcn.entitis.Item;
import com.sjhcn.qrcode.R;
import com.sjhcn.recyclerview_adapter.SecondFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjhcn on 2016/7/12.
 */
public class SecondFragment extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private TextView mTextView;

    private SecondFragmentAdapter mSecondAdapter;
    private List<Item> mData;
    private int[] bitmapId = new int[]{R.drawable.contace_circle, R.drawable.phone,
            R.drawable.wenben, R.drawable.ie,
            R.drawable.map, R.drawable.ic_find_previous_holo_dark};
    private String[] hints = new String[]{"名片", "电话", "文本", "网址", "位置"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initView(inflater, container);
        initData();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    /**
     * 初始化fragment中的view
     *
     * @param inflater
     * @param container
     * @return
     */
    private void initView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.second_fragment, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.second_recycler_view);
    }

    private void initData() {
        mData = new ArrayList<Item>(6);
        for (int i = 0; i < 5; i++) {
            Item item = new Item();
            item.setLable(((BitmapDrawable) getResources().getDrawable(bitmapId[i])).getBitmap());
            item.setArrow(((BitmapDrawable) getResources().getDrawable(bitmapId[5])).getBitmap());
            item.setContent(hints[i]);
            mData.add(item);
        }
        mSecondAdapter = new SecondFragmentAdapter(this.getActivity(), mData);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.setAdapter(mSecondAdapter);

    }


}
