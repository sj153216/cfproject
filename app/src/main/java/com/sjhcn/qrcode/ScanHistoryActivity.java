package com.sjhcn.qrcode;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.sjhcn.entitis.Item;
import com.sjhcn.module.DataManager;
import com.sjhcn.recyclerview_adapter.SimpleAdapter;
import com.sjhcn.view.MyTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong on 2016/7/13.
 */
public class ScanHistoryActivity extends BaseActivity {

    private MyTextView mScanRecord;
    private MyTextView mMakeRecord;
    private List<MyTextView> mTabs;
    private RecyclerView mRecyclerView;
    private TextView mTitle;

    private SimpleAdapter mAdapter;
    private List<Item> mDatas;
    private DataManager mDataMgr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        mScanRecord = (MyTextView) findViewById(R.id.scan_record);
        mMakeRecord = (MyTextView) findViewById(R.id.make_record);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mTitle = (TextView) findViewById(R.id.title_name);
    }

    private void initData() {
        mTitle.setText("历史记录");
        mDataMgr = DataManager.getInstance();
        mDatas = new ArrayList<Item>();
        mTabs = new ArrayList<MyTextView>(2);
        mTabs.add(mScanRecord);
        mTabs.add(mMakeRecord);
        mAdapter = new SimpleAdapter(this, mDatas);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
