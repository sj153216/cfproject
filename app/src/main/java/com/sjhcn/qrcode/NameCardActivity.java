package com.sjhcn.qrcode;

import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by tong on 2016/7/15.
 */
public class NameCardActivity extends BaseActivity {


    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_card_activity);
        initView();
        initData();
    }

    private void initData() {
        mTitle.setText("名片");
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.title_name);
    }
}
