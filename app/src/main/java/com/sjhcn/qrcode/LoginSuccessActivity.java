package com.sjhcn.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.sjhcn.myfragment.ThirdFragment;

/**
 * Created by sjhcn on 2016/8/10.
 */
public class LoginSuccessActivity extends BaseActivity {
    private TextView mTitle;
    private TextView mCountDownTv;

    private CountDownTimer mCountDownTimer = new CountDownTimer(6000, 1000) {
        int i = 5;

        @Override
        public void onTick(long millisUntilFinished) {
            mCountDownTv.setText(i-- + "");
            startAnimation();
        }

        @Override
        public void onFinish() {
            Intent intent = new Intent(LoginSuccessActivity.this, ThirdFragment.mActivity.getClass());
            startActivity(intent);
            LoginSuccessActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_success_activity);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTitle.setText("登录成功");
        mCountDownTimer.start();
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.title_name);
        mCountDownTv = (TextView) findViewById(R.id.count_down_tv);

    }

    private void startAnimation() {
        AnimationSet as = new AnimationSet(true);
        as.setDuration(1000);

        AlphaAnimation aa = new AlphaAnimation(1, 0);
        aa.setDuration(1000);
        as.addAnimation(aa);

        ScaleAnimation sa = new ScaleAnimation(0, 3, 0, 3);
        sa.setDuration(1000);
        as.addAnimation(sa);
        mCountDownTv.startAnimation(as);

    }

}
