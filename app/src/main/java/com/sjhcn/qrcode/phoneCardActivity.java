package com.sjhcn.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjhcn.constants.Constant;

/**
 * Created by sjhcn on 2016/8/15.
 */
public class PhoneCardActivity extends BaseActivity {

    private TextView mTitle;
    private EditText mPhoneEt;
    private ImageView mMakeQRcodeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_card_activity);
        initView();
        initData();
        initEvent();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mTitle.setText("电话");

    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.title_name);
        mPhoneEt = (EditText) findViewById(R.id.phone_et);
        mMakeQRcodeImg = (ImageView) findViewById(R.id.right_img);
    }

    private void initData() {

    }

    private void initEvent() {
        mMakeQRcodeImg.setVisibility(View.VISIBLE);
        mMakeQRcodeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPhoneEt.getText().toString().equals("")) {
                    Toast.makeText(PhoneCardActivity.this, "电话不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    String qrcode = mPhoneEt.getText().toString();
                    Intent intent = new Intent(PhoneCardActivity.this, MakeResultActivity.class);
                    intent.putExtra("qrCode", qrcode);
                    intent.putExtra("action", Constant.ACTION_GENERATE_PHONE_QRCODEINFO);
                    startActivity(intent);
                }
            }
        });
    }
}
