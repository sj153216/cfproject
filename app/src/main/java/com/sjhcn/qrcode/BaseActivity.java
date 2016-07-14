package com.sjhcn.qrcode;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by sjhcn on 2016/7/14.
 */
public class BaseActivity extends Activity {

    private ImageView back;
    private TextView title;
    private ImageView toRightImg;
    private ImageView rightImg;

    private String titleStr="";

    public BaseActivity() {
        super();
        back = null;
        title = null;
        toRightImg = null;
        rightImg = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //setContentView(R.layout.include_title);
        initView();
    }

    private void initView() {
    }


    @Override
    protected void onResume() {
        super.onResume();
        back = (ImageView) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
        toRightImg = (ImageView) findViewById(R.id.to_right_img);
        rightImg = (ImageView) findViewById(R.id.right_img);
        if (back != null) {
            back.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    BaseActivity.this.finish();

                }
            });
        }

        if (title != null && titleStr.length() > 0) {
            setTitle(titleStr);
        }
    }


    public void setTitle(String mTitle) {
        if (title != null) {
            titleStr = mTitle;
            title.setText(mTitle);
            title.invalidate();
        } else {
            titleStr = mTitle;
        }
    }
}
