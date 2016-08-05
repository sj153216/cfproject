package com.sjhcn.qrcode;

import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by sjhcn on 2016/8/5.
 */
public class AboutUsActivity extends BaseActivity {

    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_activity);
        mTitle = (TextView) findViewById(R.id.title_name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTitle.setText("关于我们");
    }
}
