package com.sjhcn.qrcode;

import android.os.Bundle;
import android.view.Window;

/**
 * Created by sjhcn on 2016/7/15.
 */
public class ScanResultActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scan_result_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
