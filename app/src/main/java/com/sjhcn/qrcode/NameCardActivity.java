package com.sjhcn.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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

    public static final String TYPE_NOT_USE_MODEL = "notUseModel";
    public static final String TYPE_USE_MODEL = "useModel";

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

    private Button mUseModelBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        mUseModelBt = (Button) findViewById(R.id.use_model_bt);
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
        mUseModelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateModelQRcodeStr();
            }
        });
    }

    /**
     * 获取二维码字符串,同时跳转页面
     */
    private void generateQRcodeStr() {
        getTextFromEditText();
        if (mNameStr.equals("") || mPhoneStr.equals("")) {
            Toast.makeText(NameCardActivity.this, "姓名或电话不能为空", Toast.LENGTH_SHORT).show();
        } else {
            startHandleService();
            startMakeQRcodeActivity();
        }
    }

    /**
     * 获取二维码字符串，同时跳转到模板界面
     */
    private void generateModelQRcodeStr() {
        getTextFromEditText();
        if (mNameStr.equals("") || mPhoneStr.equals("")) {
            Toast.makeText(NameCardActivity.this, "姓名或电话不能为空", Toast.LENGTH_SHORT).show();
        } else {
            startHandleService();
            startModelActivity();
        }
    }

    /**
     * 取出String
     */
    private void getTextFromEditText() {
        mNameStr = mNameEdit.getText().toString();
        mPhoneStr = mPhoneEdit.getText().toString();
        mPosStr = mPosEdit.getText().toString();
        mPartStr = mPartEdit.getText().toString();
        mEmailStr = mEmailEdit.getText().toString();
        mCompanyStr = mCompanyEdit.getText().toString();
        mQRcodeStr = mNameStr + mPhoneStr + mPosStr + mPartStr + mEmailStr + mCompanyStr;
    }

    /**
     * 启动Makeactivity
     */
    private void startMakeQRcodeActivity() {
        Intent intent = new Intent(NameCardActivity.this, MakeResultActivity.class);
        configIntent(intent, TYPE_NOT_USE_MODEL);
    }

    /**
     * 启动ModelActivity
     */
    private void startModelActivity() {
        Intent intent = new Intent(NameCardActivity.this, ModelActivity.class);
        configIntent(intent, TYPE_USE_MODEL);
    }

    /**
     * 代码冗余
     *
     * @param intent
     * @param type
     */
    private void configIntent(Intent intent, String type) {
        intent.putExtra("action", Constant.ACTION_GENERATE_NAME_QRCODEINFO);
        intent.putExtra("mNameStr", mNameStr);
        intent.putExtra("mPhoneStr", mPhoneStr);
        intent.putExtra("mPosStr", mPosStr);
        intent.putExtra("mPartStr", mPartStr);
        intent.putExtra("mEmailStr", mEmailStr);
        intent.putExtra("mCompanyStr", mCompanyStr);
        intent.putExtra("qrCode", mQRcodeStr);
        intent.putExtra("type", type);
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
