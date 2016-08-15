package com.sjhcn.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sjhcn.entitis.SuggestionInfo;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

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
                submit();
            }
        });
    }

    /**
     * 点击提交按钮
     */
    private void submit() {
        if (isEditTextEmpty()) {
            return;
        }
        if (!hasLoginIn()) {
            return;
        }
        // isRadioButtonChosed();
        submitSuggestion();

    }

    /**
     * 签名的要求都满足后，可以提交意见了
     */
    private void submitSuggestion() {
        SuggestionInfo suggestion = new SuggestionInfo();
        suggestion.setUserName(SignInActivity.sUserName);
        suggestion.setEmail(mEmailEt.getText().toString());
        suggestion.setSuggestion(mSuggestionEt.getText().toString());
        suggestion.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(SuggestionActivity.this, "提交数据成功", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            goBackToMainActivity();
                        }
                    }, 2000);
                } else {
                    Toast.makeText(SuggestionActivity.this, "提交数据失败", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    /**
     * 提交成功，跳转到主界面
     */
    private void goBackToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * 判断RadioButton有没有选择
     */
    private void isRadioButtonChosed() {
    }

    /**
     * 判断输入是否为空
     */
    private Boolean isEditTextEmpty() {
        if (mEmailEt.getText().toString().equals("") ||
                mSuggestionEt.getText().toString().equals("")) {
            Toast.makeText(this, "邮箱或意见不能为空", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    /**
     * 判断用户是否已经登录，不登录是没有权限提交意见的
     */
    private Boolean hasLoginIn() {
        if (SignInActivity.sUserName != null && SignInActivity.sPassword != null) {
            return true;
        } else {
            Toast.makeText(this, "您必须登录之后才能提交意见", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
