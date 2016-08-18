package com.sjhcn.qrcode;

import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by sjhcn on 2016/8/18.
 */
public class ShowLocationActivity extends BaseActivity {
    private TextView mTitle;
    private TextView mShowLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_location_activity);
        initView();
        initData();
    }

    private void initData() {
        String location = getIntent().getStringExtra("location");
        mShowLocation.setText(location);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTitle.setText("地理位置");
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.title_name);
        mShowLocation = (TextView) findViewById(R.id.show_location_tv);

    }
}
