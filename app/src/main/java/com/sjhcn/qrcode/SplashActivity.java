package com.sjhcn.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

/**
 * Created by sjhcn on 2016/7/11.
 */
public class SplashActivity extends Activity {
    private ImageView mInitImg;
    private static final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        initView();
        initAnimation();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    private void initView() {
        mInitImg = (ImageView) findViewById(R.id.init_img);
    }

    /**
     * 设置imageview的动画
     */
    private void initAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(2000);//设置动画持续时间
        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        animation.setStartOffset(500);//执行前的等待时间
        mInitImg.setAnimation(animation);
        animation.startNow();
    }
}
