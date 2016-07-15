package com.sjhcn.myfragment;

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
import com.sjhcn.recyclerview_adapter.FourthFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjhcn on 2016/7/12.
 */
public class FourthFragment extends Fragment {
    private View view;
    private RecyclerView mRecyclerView;
    private TextView mTextView;
    private TextView mFragmentTitle;

    private FourthFragmentAdapter mFourthAdapter;
    private List<Item> mData;
    private int[] bitmapId = new int[]{R.drawable.btn_rating_star_on_normal_holo_dark, R.drawable.ic_menu_invite,
            R.drawable.ic_menu_invite, R.drawable.ic_menu_invite, R.drawable.ic_menu_invite, R.drawable.ic_find_previous_holo_dark};

    private String[] hints = new String[]{"关于我们", "版本升级", "分享给好友", "意见反馈", "二维码签到"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initView(inflater, container);
        initData();
        return view;
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
        mTextView = (TextView) view.findViewById(R.id.tv);
        mFragmentTitle = (TextView) view.findViewById(R.id.fragment_title);
    }

    private void initData() {
        mFragmentTitle.setText("设置");
        mData = new ArrayList<Item>(5);
        for (int i = 0; i < 5; i++) {
            Item item = new Item();
            item.setLable(((BitmapDrawable) getResources().getDrawable(bitmapId[i])).getBitmap());
            item.setArrow(((BitmapDrawable) getResources().getDrawable(bitmapId[5])).getBitmap());
            item.setContent(hints[i]);
            mData.add(item);
        }
        mFourthAdapter = new FourthFragmentAdapter(this.getActivity(), mData);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.setAdapter(mFourthAdapter);

    }


}
