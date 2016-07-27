package com.sjhcn.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.sjhcn.constants.Constant;
import com.sjhcn.view.SwitchView;
import com.sjhcn.zxingencoding.EncodingHandler;

/**
 * Created by sjhcn on 2016/7/15.
 */
public class MakeResultActivity extends BaseActivity {
    private SwitchView mSwitchView;

    //下面是网址部分的控件跟内容
    private RelativeLayout mUrlRl;
    private TextView mContentTv;
    private Button mAccessToBt;

    //下面是名片部分的控件跟内容
    private LinearLayout mNameLl;
    private TextView mNameTv;
    private TextView mPosTv;
    private TextView mPhoneTv;
    private RelativeLayout mCompanyRl;
    private RelativeLayout mPartRl;
    private RelativeLayout mEmailRl;
    private TextView mCompanyTv;
    private TextView mPartTv;
    private TextView mEmailTv;

    //下面是电话部分
    private RelativeLayout mPhoneRl;
    private ImageView mPhoneIv;
    private TextView mPhoneContentTv;

    //下面是通过switchview打开或关闭的二维码图标控件
    private ImageView mQRcodeBitmap;
    private RelativeLayout mQRcodeRl;

    //标题，根据action变化
    private TextView mTitle;
    //二维码字符串，用于生成二维码
    private String mQRcode;
    //根据此action判断是显示url部分还是名片部分，还有title
    private int mAction;
    //通过制码传递过来的qrcode
    private String mQRcodeInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.make_result_activity);
        initView();
        initData();
        initEvent();


    }

    private void initView() {
        //初始化url部分的view
        mUrlRl = (RelativeLayout) findViewById(R.id.url_rl);
        mContentTv = (TextView) findViewById(R.id.contentTv);
        mAccessToBt = (Button) findViewById(R.id.make_access_to);
        //初始化名片部分的view
        mNameLl = (LinearLayout) findViewById(R.id.name_ll);
        mNameTv = (TextView) findViewById(R.id.nameTv);
        mPosTv = (TextView) findViewById(R.id.posTv);
        mPhoneTv = (TextView) findViewById(R.id.phoneTv);
        mCompanyRl = (RelativeLayout) findViewById(R.id.company_rl);
        mPartRl = (RelativeLayout) findViewById(R.id.part_rl);
        mEmailRl = (RelativeLayout) findViewById(R.id.email_rl);
        mCompanyTv = (TextView) findViewById(R.id.companyTv);
        mPartTv = (TextView) findViewById(R.id.partTv);
        mEmailTv = (TextView) findViewById(R.id.emailTv);

        //初始化map部分的view

        //显示二维码图标的按钮
        mSwitchView = (SwitchView) findViewById(R.id.switchview);
        //下面两个就是隐藏的二维码图片
        mQRcodeBitmap = (ImageView) findViewById(R.id.qrcode_bitmap);
        mQRcodeRl = (RelativeLayout) findViewById(R.id.qrcode_rl);
        mTitle = (TextView) findViewById(R.id.title_name);

        //初始化电话部分
        mPhoneRl = (RelativeLayout) findViewById(R.id.phone_rl);
        mPhoneIv = (ImageView) findViewById(R.id.phone_lable);
        mPhoneContentTv = (TextView) findViewById(R.id.phone_content_tv);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAction == Constant.ACTION_GENERATE_URL_QRCODEINFO) {
            mTitle.setText("网址二维码");
        } else if (mAction == Constant.ACTION_GENERATE_NAME_QRCODEINFO) {
            mTitle.setText("名片二维码");
        } else if (mAction == Constant.ACTION_GENERATE_PHONE_QRCODEINFO) {
            mTitle.setText("电话二维码");
        } else {
            mTitle.setText("地图二维码");
        }
    }

    private void initData() {
        Intent intent = getIntent();
        mAction = intent.getIntExtra("action", Constant.ACTION_GENERATE_URL_QRCODEINFO);
        mQRcodeInfo = intent.getStringExtra("qrCode");
        showWhichPage(mAction, intent);
    }

    /**
     * 根据action判断显示哪个界面，是url还是名片
     *
     * @param action
     */
    private void showWhichPage(int action, Intent intent) {
        switch (action) {
            case Constant.ACTION_GENERATE_URL_QRCODEINFO:
                //首先让另外两个布局不可见
                mNameLl.setVisibility(View.GONE);
                mUrlRl.setVisibility(View.VISIBLE);
                mContentTv.setText(mQRcodeInfo);
                break;
            case Constant.ACTION_GENERATE_NAME_QRCODEINFO:
                //首先让另外两个布局不可见
                mUrlRl.setVisibility(View.GONE);
                mNameLl.setVisibility(View.VISIBLE);
                String mNameStr = intent.getStringExtra("mNameStr");
                String mPhoneStr = intent.getStringExtra("mPhoneStr");
                String mPosStr = intent.getStringExtra("mPosStr");
                String mPartStr = intent.getStringExtra("mPartStr");
                String mEmailStr = intent.getStringExtra("mEmailStr");
                String mCompanyStr = intent.getStringExtra("mCompanyStr");

                mNameTv.setText(mNameStr);
                mPhoneTv.setText(mPhoneStr);
                mPosTv.setText(mPosStr);
                mPartTv.setText(mPartStr);
                mEmailTv.setText(mEmailStr);
                mCompanyTv.setText(mCompanyStr);
                break;
            case Constant.ACTION_GENERATE_PHONE_QRCODEINFO:
                mNameLl.setVisibility(View.GONE);
                mUrlRl.setVisibility(View.GONE);
                mPhoneRl.setVisibility(View.VISIBLE);
                mPhoneContentTv.setText(mQRcodeInfo);
                break;
            default:
                break;
        }
    }

    /**
     * 根据action将对应的内容设置到view
     *
     * @param action
     */
    private void setContentToView(int action) {
        switch (action) {
            case Constant.ACTION_GENERATE_URL_QRCODEINFO:
                break;
            case Constant.ACTION_GENERATE_NAME_QRCODEINFO:
                break;
            case Constant.ACTION_GENERATE_MAP_QRCODEINFO:
                break;
            default:
                break;
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

        mAccessToBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mContentTv.getText().toString();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
            }
        });
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
        mQRcodeRl.startAnimation(animation);
        mQRcodeRl.setVisibility(View.GONE);
    }

    /**
     * 点击switchView时，动画的弹出显示二维码的imageview
     *
     * @throws WriterException
     */
    private void openQRbitmap() throws WriterException {
        mSwitchView.setOpened(true);
        if (!mQRcodeInfo.equals("")) {
            //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（200*200）
            Bitmap qrCodeBitmap = EncodingHandler.createQRCode(mQRcodeInfo, 200);

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
            mQRcodeRl.setVisibility(View.VISIBLE);
            mQRcodeBitmap.setImageBitmap(bitmap);
            ScaleAnimation animation = new ScaleAnimation(0.0f, 1.1f, 0.0f, 1.1f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(500);//设置动画持续时间
            animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
            mQRcodeRl.startAnimation(animation);
        } else {
            Toast.makeText(MakeResultActivity.this, "Text can not be empty", Toast.LENGTH_SHORT).show();
        }
    }


}
