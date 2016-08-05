package com.sjhcn.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjhcn.constants.Constant;
import com.sjhcn.intf.LoadDataIntf;
import com.sjhcn.service.HandleQRcodeService;

/**
 * Created by tong on 2016/7/15.
 */
public class NameCardActivity extends BaseActivity implements LoadDataIntf {


    private TextView mTitle;
    private ImageView mMakeQRcodeImg;
    private EditText mNameEdit;
    private EditText mPhoneEdit;
    private EditText mPosEdit;
    private EditText mPartEdit;
    private EditText mEmailEdit;
    private EditText mCompanyEdit;

    //生成二维码的字符串
    private String mQRcodeStr;
    private String mNameStr;
    private String mPhoneStr;
    private String mPosStr;
    private String mPartStr;
    private String mEmailStr;
    private String mCompanyStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_card_activity);
        initView();
        initData();
        initEvent();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mTitle.setText("名片");
        mMakeQRcodeImg.setVisibility(View.VISIBLE);
    }


    private void initView() {
        mTitle = (TextView) findViewById(R.id.title_name);
        mMakeQRcodeImg = (ImageView) findViewById(R.id.right_img);
        mNameEdit = (EditText) findViewById(R.id.name);
        mPhoneEdit = (EditText) findViewById(R.id.phone);
        mPosEdit = (EditText) findViewById(R.id.position);
        mPartEdit = (EditText) findViewById(R.id.part);
        mEmailEdit = (EditText) findViewById(R.id.email);
        mCompanyEdit = (EditText) findViewById(R.id.company);
    }

    private void initData() {

    }


    private void initEvent() {
        mMakeQRcodeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateQRcodeStr();
            }
        });
    }

    /**
     * 获取二维码字符串
     */
    private void generateQRcodeStr() {
        mNameStr = mNameEdit.getText().toString();
        mPhoneStr = mPhoneEdit.getText().toString();

        mPosStr = mPosEdit.getText().toString();
        mPartStr = mPartEdit.getText().toString();
        mEmailStr = mEmailEdit.getText().toString();
        mCompanyStr = mCompanyEdit.getText().toString();

        mQRcodeStr = mNameStr + mPhoneStr + mPosStr + mPartStr + mEmailStr + mCompanyStr;

        if (mNameStr.equals("") || mPhoneStr.equals("")) {
            Toast.makeText(NameCardActivity.this, "姓名或电话不能为空", Toast.LENGTH_SHORT).show();
        } else {
            startHandleService();
            startMakeQRcodeActivity();
        }
    }

    /**
     * 启动activity
     */
    private void startMakeQRcodeActivity() {
        Intent intent = new Intent(NameCardActivity.this, MakeResultActivity.class);
        intent.putExtra("action", Constant.ACTION_GENERATE_NAME_QRCODEINFO);
        intent.putExtra("mNameStr", mNameStr);
        intent.putExtra("mPhoneStr", mPhoneStr);
        intent.putExtra("mPosStr", mPosStr);
        intent.putExtra("mPartStr", mPartStr);
        intent.putExtra("mEmailStr", mEmailStr);
        intent.putExtra("mCompanyStr", mCompanyStr);
        intent.putExtra("qrCode", mQRcodeStr);
        startActivity(intent);
    }

    /**
     * 启动service，存储制码数据到数据库
     */
    private void startHandleService() {
        Intent intent = new Intent(NameCardActivity.this, HandleQRcodeService.class);
        intent.setAction(HandleQRcodeService.ACTION_SAVE_MAKE_TO_LOCAL);
        intent.putExtra("serviceResult", mQRcodeStr);
        startService(intent);
    }

    @Override
    public void onLoadFinish(int action) {

    }

    @Override
    public void onLoadStart() {

    }
}
