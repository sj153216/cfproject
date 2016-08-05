package com.sjhcn.qrcode;

import android.os.Bundle;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by sjhcn on 2016/7/15.
 */
public class CollectActivity extends BaseActivity {

    private TextView mTitle;
    private RelativeLayout mNoRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.collect_activity);
        initView();
        initData();
        initEvent();


    }

    private void initData() {

    }

    private void initView() {
        mNoRl = (RelativeLayout) findViewById(R.id.no_rl);
        mTitle = (TextView) findViewById(R.id.title_name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTitle.setText("我的收藏");
    }


    private void initEvent() {

    }


}
