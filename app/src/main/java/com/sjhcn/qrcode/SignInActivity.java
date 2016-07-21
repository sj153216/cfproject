package com.sjhcn.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sjhcn.entitis.UserInfo;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by sjhcn on 2016/8/8.
 */
public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTitle;
    private Button mSignInBt;
    private EditText mUserNameEt;
    private EditText mPasswordEt;
    private RelativeLayout mRegiRl;

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
        mRegiRl.setOnClickListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        mTitle.setText("用户登录");
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.title_name);
        mSignInBt = (Button) findViewById(R.id.signIn_bt);
        mUserNameEt = (EditText) findViewById(R.id.username_et);
        mPasswordEt = (EditText) findViewById(R.id.password_et);
        mRegiRl = (RelativeLayout) findViewById(R.id.regi_rl);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.regi_rl:
                //用户注册
                Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
                startActivity(intent);

                break;
            case R.id.signIn_bt:
                //用户登录

                UserInfo userInfo = new UserInfo();
                if (mUserNameEt.getText().toString().equals("") || mPasswordEt.getText().toString().equals("")) {
                    Toast.makeText(SignInActivity.this, "用户名跟密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    userInfo.setUserName(mUserNameEt.getText().toString());
                    userInfo.setPassword(mPasswordEt.getText().toString());
                    userInfo.login(new SaveListener() {
                        @Override
                        public void done(Object o, Object o2) {
                            Toast.makeText(SignInActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void done(Object o, BmobException e) {

                        }
                    });
                }
                SignInActivity.this.finish();
                break;

        }

    }
}
