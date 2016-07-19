package com.sjhcn.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.sjhcn.zxingencoding.EncodingHandler;

/**
 * Created by sjhcn on 2016/7/15.
 */
public class ScanResultActivity extends BaseActivity {
    private ImageView mLable;
    private TextView mContent;
    private String QRcode;
    private TextView mTitle;
    private ImageView mInner;
    private ImageView mQRcodeBitmap;

    private Drawable urlDrawable;
    private Drawable normalDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scan_result_activity);
        initView();
        initData();
        initEvent();


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
        mInner = (ImageView) findViewById(R.id.inner);
        mQRcodeBitmap = (ImageView) findViewById(R.id.qrcode_bitmap);

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


    private void initEvent() {
        mInner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String contentString = mContent.getText().toString();
                    if (!contentString.equals("")) {
                        //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（600*600）
                        Bitmap qrCodeBitmap = EncodingHandler.createQRCode(contentString, 600);

                        //------------------添加logo部分------------------//
                        Bitmap logoBmp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_rating_star_on_normal_holo_dark);

                        //二维码和logo合并
                        Bitmap bitmap = Bitmap.createBitmap(qrCodeBitmap.getWidth(), qrCodeBitmap
                                .getHeight(), qrCodeBitmap.getConfig());
                        Canvas canvas = new Canvas(bitmap);
                        //二维码
                        canvas.drawBitmap(qrCodeBitmap, 0, 0, null);
                        //logo绘制在二维码中央
                        canvas.drawBitmap(logoBmp, qrCodeBitmap.getWidth() / 2
                                - logoBmp.getWidth() / 2, qrCodeBitmap.getHeight()
                                / 2 - logoBmp.getHeight() / 2, null);
                        //------------------添加logo部分------------------//

                        mQRcodeBitmap.setImageBitmap(bitmap);
                    } else {
                        Toast.makeText(ScanResultActivity.this, "Text can not be empty", Toast.LENGTH_SHORT).show();
                    }

                } catch (WriterException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

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
