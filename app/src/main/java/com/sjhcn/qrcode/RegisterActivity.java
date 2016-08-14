package com.sjhcn.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sjhcn.entitis.UserInfo;

import org.json.JSONArray;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
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
    //注册时先判断用户名是否注册过
    private Boolean isRepeatUserName = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
            if (!isRepeatUserName) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUserName(mUserNameEt.getText().toString());
                userInfo.setPassword(mPasswordEt.getText().toString());
                userInfo.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            //Toast.makeText(RegisterActivity.this, "添加数据成功", Toast.LENGTH_SHORT).show();
                            startRegisterSuccessActivity();
                        } else {
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            } else {
                Toast.makeText(RegisterActivity.this, "该用户名已被注册", Toast.LENGTH_SHORT).show();
            }
        }

    }


    /**
     * 判断当前用户名是否注册guo
     */
    private Boolean isRepeatUserName() {

        final String userName = mUserNameEt.getText().toString();
        BmobQuery query = new BmobQuery("UserInfo");
        query.addWhereEqualTo("userName", userName);
        query.order("createdAt");
        //v3.5.0版本提供`findObjectsByTable`方法查询自定义表名的数据
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray jsonArray, BmobException e) {
                if (jsonArray == null) {
                    isRepeatUserName = false;
                } else {
                    isRepeatUserName = true;
                }
            }
        });
        return isRepeatUserName;
    }

    /**
     * 跳转到注册成功界面
     */
    private void startRegisterSuccessActivity() {
        Intent intent = new Intent(RegisterActivity.this, RegisterSuccessActivity.class);
        startActivity(intent);
        RegisterActivity.this.finish();
    }
}
