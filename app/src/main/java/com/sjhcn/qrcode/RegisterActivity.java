package com.sjhcn.qrcode;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by sjhcn on 2016/8/8.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTitle;
    private Button mRegisterBt;

    private EditText mUserNameEt;
    private EditText mConfirmEt;
    private EditText mPasswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initView();
        initEvent();
    }

    private void initEvent() {
        mRegisterBt.setOnClickListener(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        mTitle.setText("用户注册");
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.title_name);
        mRegisterBt = (Button) findViewById(R.id.register_bt);
        mConfirmEt = (EditText) findViewById(R.id.confirm_et);
        mUserNameEt = (EditText) findViewById(R.id.username_regi_et);
        mPasswordEt = (EditText) findViewById(R.id.password_regi_et);

    }

    @Override
    public void onClick(View v) {
        //用户注册
        if (mUserNameEt.getText().toString().equals("") ||
                mPasswordEt.getText().toString().equals("") ||
                mConfirmEt.getText().toString().equals("")) {
            Toast.makeText(RegisterActivity.this, "账户名或密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            BmobUser user = new BmobUser();

            user.setUsername(mUserNameEt.getText().toString());
            user.setPassword(mPasswordEt.getText().toString());
            user.signUp(new SaveListener() {
                @Override
                public void done(Object o, Object o2) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void done(Object o, BmobException e) {
                    if (e == null) {

                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }
                }


            });
//        userInfo.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null) {
//                    Toast.makeText(RegisterActivity.this, "添加数据成功", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(RegisterActivity.this, RegisterSuccessActivity.class);
//                    startActivity(intent);
//                    RegisterActivity.this.finish();
//                } else {
//                    Toast.makeText(RegisterActivity.this, "添加数据失败", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });

        }
    }
}