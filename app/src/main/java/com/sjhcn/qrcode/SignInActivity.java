package com.sjhcn.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sjhcn.entitis.UserInfo;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by sjhcn on 2016/8/8.
 */
public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTitle;
    private Button mSignInBt;
    private TextView mRegesterTv;
    private EditText mUserNameEt;
    private EditText mPasswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity);
        initView();
        initData();
        initEvent();
    }

    private void initData() {
        Bmob.initialize(this, "75e152813db897d7e9daf784b0b8a2fa");
    }

    private void initEvent() {
        mRegesterTv.setOnClickListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        mTitle.setText("用户登录");
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.title_name);
        mSignInBt = (Button) findViewById(R.id.signIn_bt);
        mRegesterTv = (TextView) findViewById(R.id.regeste_tv);
        mUserNameEt = (EditText) findViewById(R.id.username_et);
        mPasswordEt = (EditText) findViewById(R.id.password_et);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.regeste_tv:
                //用户注册
                Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
                startActivity(intent);

                break;
            case R.id.signIn_bt:
                //用户登录
                BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
                query.getObject("sj153216", new QueryListener<UserInfo>() {
                    @Override
                    public void done(UserInfo object, BmobException e) {
                        if (e == null) {
                            Toast.makeText(SignInActivity.this, "添加数据成功", Toast.LENGTH_SHORT).show();
                            String ps = object.getPassword();
                        } else {
                            Toast.makeText(SignInActivity.this, "添加数据成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }

    }
}
