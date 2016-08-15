package com.sjhcn.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.sjhcn.view.SwitchView;
import com.sjhcn.zxingencoding.EncodingHandler;

import static com.sjhcn.qrcode.R.id.to_right_img;

/**
 * Created by sjhcn on 2016/7/15.
 */
public class ScanResultActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mLable;
    private TextView mContent;
    private String QRcode;
    private TextView mTitle;
    private ImageView mQRcodeBitmap;
    private SwitchView mSwitchView;
    private ImageView mShareIv;

    private RelativeLayout mRl;
    private Drawable urlDrawable;
    private Drawable normalDrawable;
    private Button mAccessBt;

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
        urlDrawable = getResources().getDrawable(R.drawable.ie);
        normalDrawable = getResources().getDrawable(R.drawable.contace_circle);
        mShareIv.setVisibility(View.VISIBLE);
    }

    private void initView() {
        mLable = (ImageView) findViewById(R.id.lable);
        mContent = (TextView) findViewById(R.id.content);
        mTitle = (TextView) findViewById(R.id.title_name);
        mSwitchView = (SwitchView) findViewById(R.id.switchview);
        mQRcodeBitmap = (ImageView) findViewById(R.id.qrcode_bitmap);
        mRl = (RelativeLayout) findViewById(R.id.rl);
        mAccessBt = (Button) findViewById(R.id.scan_access_to);
        mShareIv = (ImageView) findViewById(to_right_img);
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
        mSwitchView.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                try {
                    openQRbitmap();
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void toggleToOff(View view) {
                closeQRbitmap();
            }
        });

        mAccessBt.setOnClickListener(this);
        mShareIv.setOnClickListener(this);
    }

    /**
     * 点击switchview时关闭显示二维码的imageview
     */
    private void closeQRbitmap() {
        mSwitchView.setOpened(false);
        ScaleAnimation animation = new ScaleAnimation(1.1f, 0.0f, 1.1f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(500);//设置动画持续时间
        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        mRl.startAnimation(animation);
        mRl.setVisibility(View.GONE);
    }

    /**
     * 点击switchView时，动画的弹出显示二维码的imageview
     *
     * @throws WriterException
     */
    private void openQRbitmap() throws WriterException {
        mSwitchView.setOpened(true);
        String contentString = mContent.getText().toString();
        if (!contentString.equals("")) {
            //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（200*200）
            Bitmap qrCodeBitmap = EncodingHandler.createQRCode(contentString, 150);

            //------------------添加logo部分------------------//
            Bitmap logoBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_invite);

            //二维码和logo合并
            Bitmap bitmap = Bitmap.createBitmap(qrCodeBitmap.getWidth(), qrCodeBitmap
                    .getHeight(), qrCodeBitmap.getConfig());
            Canvas canvas = new Canvas(bitmap);
            //二维码
            canvas.drawBitmap(qrCodeBitmap, 0, 0, null);
            //logo绘制在二维码中央
//            canvas.drawBitmap(logoBmp, qrCodeBitmap.getWidth() / 2
//                    - logoBmp.getWidth() / 2, qrCodeBitmap.getHeight()
//                    / 2 - logoBmp.getHeight() / 2, null);
            //------------------添加logo部分------------------//
            mRl.setVisibility(View.VISIBLE);
            mQRcodeBitmap.setImageBitmap(bitmap);
            ScaleAnimation animation = new ScaleAnimation(0.0f, 1.1f, 0.0f, 1.1f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(500);//设置动画持续时间
            animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
            mRl.startAnimation(animation);
        } else {
            Toast.makeText(ScanResultActivity.this, "Text can not be empty", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan_access_to:
                String url = (String) mContent.getText();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
                break;
            case R.id.to_right_img:
                //分享的代码

                break;
        }
    }
}
