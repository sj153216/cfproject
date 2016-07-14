package com.sjhcn.myfragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjhcn.constants.Constant;
import com.sjhcn.entitis.Item;
import com.sjhcn.intf.LoadDataIntf;
import com.sjhcn.qrcode.R;
import com.sjhcn.qrcode.ScanHistoryActivity;
import com.sjhcn.recyclerview_adapter.SimpleAdapter;
import com.sjhcn.view.MyProgressDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjhcn on 2016/7/12.
 */
public class ThirdFragment extends Fragment implements LoadDataIntf {
    private View view;
    private RecyclerView mRecyclerView;
    private TextView mTextView;

    private SimpleAdapter mSimpleAdapter;
    private List<Item> mData;
    private int[] bitmapId = new int[]{R.drawable.btn_rating_star_on_normal_holo_dark, R.drawable.ic_menu_invite,
            R.drawable.ic_menu_invite, R.drawable.ic_find_previous_holo_dark};

    private String[] hints = new String[]{"我的收藏", "历史记录", "应用码分享"};
    private MyProgressDialog dialog;
    private Context mActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initView(inflater, container);
        initData();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
    }

    private void initData() {
        mData = new ArrayList<Item>(3);
        for (int i = 0; i < 3; i++) {
            Item item = new Item();
            item.setLable(((BitmapDrawable) getResources().getDrawable(bitmapId[i])).getBitmap());
            item.setArrow(((BitmapDrawable) getResources().getDrawable(bitmapId[3])).getBitmap());
            item.setContent(hints[i]);
            mData.add(item);
        }
        mSimpleAdapter = new SimpleAdapter(this.getActivity(), mData);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mSimpleAdapter);

    }

    @Override
    public void onLoadFinish(int action) {
        if (dialog != null)
            dialog.dismiss();
        switch (action) {
            case Constant.ACTION_LOAD_QRCODEINFO:
                Intent intent = new Intent(mActivity, ScanHistoryActivity.class);
                mActivity.startActivity(intent);
                break;
        }
    }

    @Override
    public void onLoadStart() {
//        dialog = new MyProgressDialog(QRcodeApplication.getInstance());
//        dialog.show();
    }
}
