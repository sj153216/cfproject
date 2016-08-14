package com.sjhcn.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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
public class UrlCardActivity extends BaseActivity implements LoadDataIntf {

    private TextView mTitle;
    private ImageView mMakeQRcodeImg;
    private EditText mUrlEt;
    private String mUrlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.url_card_activity);
        initView();
        initData();
        initEvent();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mTitle.setText("网址");
        mMakeQRcodeImg.setVisibility(View.VISIBLE);
    }


    private void initView() {
        mTitle = (TextView) findViewById(R.id.title_name);
        mMakeQRcodeImg = (ImageView) findViewById(R.id.right_img);
        mUrlEt = (EditText) findViewById(R.id.urlEt);
    }

    private void initData() {

    }


    private void initEvent() {
        mMakeQRcodeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQRcodeStr();
            }
        });
    }

    /**
     * 获取二维码字符串
     */
    private void getQRcodeStr() {
        mUrlContent = mUrlEt.getText().toString();
        if (mUrlContent.equals("")) {
            Toast.makeText(UrlCardActivity.this, "网址不能为空", Toast.LENGTH_SHORT).show();
        } else {
            startHandleService();
            startMakeQRcodeActivity();
        }
    }

    /**
     * 启动activity
     */
    private void startMakeQRcodeActivity() {
        Intent intent = new Intent(UrlCardActivity.this, MakeResultActivity.class);
        intent.putExtra("action", Constant.ACTION_GENERATE_URL_QRCODEINFO);
        intent.putExtra("qrCode", mUrlContent);
        startActivity(intent);
    }

    /**
     * 启动service，存储制码数据到数据库
     */
    private void startHandleService() {
        Intent intent = new Intent(UrlCardActivity.this, HandleQRcodeService.class);
        intent.setAction(HandleQRcodeService.ACTION_SAVE_MAKE_TO_LOCAL);
        intent.putExtra("serviceResult", mUrlContent);
        startService(intent);
    }

    @Override
    public void onLoadFinish(int action) {

    }

    @Override
    public void onLoadStart() {

    }
}
