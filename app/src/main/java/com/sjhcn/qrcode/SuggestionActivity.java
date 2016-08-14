package com.sjhcn.qrcode;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by sjhcn on 2016/8/14.
 */
public class SuggestionActivity extends BaseActivity {

    private TextView mTitle;
    private EditText mEmailEt;
    private EditText mSuggestionEt;
    private RadioButton mOperateRbt;
    private RadioButton mFuctionRbt;
    private Button mSubmitBt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.suggestion_activity);
        initView();
        initData();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTitle.setText("意见反馈");
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.title_name);
        mEmailEt = (EditText) findViewById(R.id.email_et);
        mSuggestionEt = (EditText) findViewById(R.id.suggestion_et);
        mOperateRbt = (RadioButton) findViewById(R.id.suggestion_rbt);
        mFuctionRbt = (RadioButton) findViewById(R.id.fuction_rbt);
        mSubmitBt = (Button) findViewById(R.id.submit_bt);
    }

    private void initData() {

    }

    private void initEvent() {
        mSubmitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
