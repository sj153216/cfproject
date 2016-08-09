package com.sjhcn.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sjhcn.entitis.UserInfo;

import org.json.JSONArray;

import java.util.ArrayList;

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
    private EditText mUserNameEt;
    private EditText mPasswordEt;
    private RelativeLayout mRegiRl;

    //关于用户名跟密码
    public static String sUserName;
    public static String sPassword;
    //是否已经登录过
    public static Boolean sHasLoginIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_in_activity);
        initView();
        initData();
        initEvent();

    }

    private void initData() {
        Bmob.initialize(this, "75e152813db897d7e9daf784b0b8a2fa");
    }

    private void initEvent() {
        mRegiRl.setOnClickListener(this);
        mSignInBt.setOnClickListener(this);

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
                SignInActivity.this.finish();
                break;
            case R.id.signIn_bt:
                //用户登录
                loginIn();
                break;

        }
    }

    /**
     * 用户登录
     */
    private void loginIn() {
        if (mUserNameEt.getText().toString().equals("") ||
                mPasswordEt.getText().toString().equals("")) {
            Toast.makeText(SignInActivity.this, "账户名或密码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            final String userName = mUserNameEt.getText().toString();
            final String password = mPasswordEt.getText().toString();
            BmobQuery query = new BmobQuery("UserInfo");
            query.addWhereEqualTo("userName", userName);
            query.order("createdAt");
            //v3.5.0版本提供`findObjectsByTable`方法查询自定义表名的数据
            query.findObjectsByTable(new QueryListener<JSONArray>() {
                @Override
                public void done(JSONArray jsonArray, BmobException e) {
                    judgePerssion(jsonArray, e, password);
                }
            });
        }
    }

    /**
     * 判断用户名跟密码是否有权限
     *
     * @param jsonArray
     * @param e
     * @param password
     */
    private void judgePerssion(JSONArray jsonArray, BmobException e, String password) {
        if (e == null) {
            if (jsonArray == null || jsonArray.length() == 0) {
                Toast.makeText(SignInActivity.this, "该用户未注册", Toast.LENGTH_SHORT).show();
            } else {
                ArrayList<UserInfo> userInfoList = (ArrayList<UserInfo>) com.alibaba.fastjson.JSONArray.parseArray(jsonArray.toString(), UserInfo.class);
                UserInfo userInfo = userInfoList.get(0);
                if (sUserName == null) {
                    if (userInfo.getPassword().equals(password)) {
                        sUserName = mUserNameEt.getText().toString();
                        startLoginSuccessActivity();
                    } else {
                        Toast.makeText(SignInActivity.this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (sUserName.equals(mUserNameEt.getText().toString())) {
                        Toast.makeText(SignInActivity.this, "当前用户已经登录,不可重复登录", Toast.LENGTH_SHORT).show();
                    }else{
                        sUserName = mUserNameEt.getText().toString();
                        startLoginSuccessActivity();
                    }
                }

            }
        }
    }

    /**
     * 跳转到登录成功界面
     */
    private void startLoginSuccessActivity() {
        Intent intent = new Intent(this, LoginSuccessActivity.class);
        startActivity(intent);
        SignInActivity.this.finish();

    }

}



