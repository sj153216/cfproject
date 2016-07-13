package com.sjhcn.qrcode;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by sjhcn on 2016/7/15.
 */
public class ScanResultActivity extends BaseActivity {
    private ImageView mLable;
    private TextView mContent;
    private String QRcode;
    private TextView mTitle;

    private Drawable urlDrawable;
    private Drawable normalDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scan_result_activity);
        initView();
        initData();


    }

    private void initData() {
        Intent intent = getIntent();
        QRcode = intent.getStringExtra("result");
        urlDrawable = getResources().getDrawable(R.drawable.ic_menu_add);
        normalDrawable = getResources().getDrawable(R.drawable.ic_menu_invite);
    }

    private void initView() {
        mLable = (ImageView) findViewById(R.id.lable);
        mContent = (TextView) findViewById(R.id.content);
        mTitle = (TextView) findViewById(R.id.title_name);
    }


    @Override
    protected void onResume() {
        super.onResume();
        String head = QRcode.substring(0, 4);
        if (head.equals("http")) {
            setLableAndTitle("网址二维码", urlDrawable);
        } else {
            setLableAndTitle("正常二维码", normalDrawable);

        }

    }

    /**
     * 根据是网址还是正常的二维码设置title，lable
     *
     * @param title
     * @param drawable
     */
    private void setLableAndTitle(String title, Drawable drawable) {
        mTitle.setText(title);
        mLable.setImageDrawable(drawable);
        mContent.setText(QRcode);
    }
}
