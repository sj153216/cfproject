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
import com.sjhcn.utils.Utils;
import com.sjhcn.view.SwitchView;
import com.sjhcn.zxingencoding.EncodingHandler;

import java.io.File;
import java.io.FileOutputStream;

import cn.sharesdk.framework.Platform;

/**
 * Created by sjhcn on 2016/7/15.
 */
public class MakeResultActivity extends BaseActivity implements View.OnClickListener {
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

    //下面是地图部分
    private ImageView mShowMapIv;
    private LinearLayout mMapLl;
    private TextView mMapContentTv;

    //下面是我的二维码部分
    private TextView mMyCodeTv;


    //标题，根据action变化
    private TextView mTitle;
    //标题，根据action变化
    private ImageView mShareIv;
    //二维码字符串，用于生成二维码
    private String mQRcode;
    //根据此action判断是显示url部分还是名片部分，还有title
    private int mAction;
    //通过制码传递过来的qrcode
    private String mQRcodeInfo;
    //地图界面传递过来的字节数组
    private byte[] mMapByte;
    //将地图界面传递过来的字节数组转化为bitmap
    private Bitmap mMapBitmap;
    //将模板界面传递过来的字节数组转化为bitmap
    private Bitmap mBitmap;


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
        mShowMapIv = (ImageView) findViewById(R.id.mapshow_iv);
        mMapLl = (LinearLayout) findViewById(R.id.map_ll);
        mMapContentTv = (TextView) findViewById(R.id.map_content_tv);
        //显示二维码图标的按钮
        mSwitchView = (SwitchView) findViewById(R.id.switchview);
        //下面两个就是隐藏的二维码图片
        mQRcodeBitmap = (ImageView) findViewById(R.id.qrcode_bitmap);
        mQRcodeRl = (RelativeLayout) findViewById(R.id.qrcode_rl);
        mTitle = (TextView) findViewById(R.id.title_name);
        mShareIv = (ImageView) findViewById(R.id.to_right_img);

        //初始化电话部分
        mPhoneRl = (RelativeLayout) findViewById(R.id.phone_rl);
        mPhoneIv = (ImageView) findViewById(R.id.phone_lable);
        mPhoneContentTv = (TextView) findViewById(R.id.phone_content_tv);

        //初始化我的二维码部分
        mMyCodeTv = (TextView) findViewById(R.id.my_code_tv);
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
        } else if (mAction == Constant.ACTION_GENERATE_MAP_QRCODEINFO) {
            mTitle.setText("地图二维码");
        } else if (mAction == Constant.ACTION_GENERATE_NAME_MODEL_QRCODEINFO ||
                mAction == Constant.ACTION_GENERATE_PHONE_MODEL_QRCODEINFO ||
                mAction == Constant.ACTION_GENERATE_URL_MODEL_QRCODEINFO) {
            mTitle.setText("模板二维码");
        } else if (mAction == Constant.ACTION_GENERATE_MYCODE_QRCODEINFO) {
            mTitle.setText("我的二维码");
        }
        mShareIv.setVisibility(View.VISIBLE);
    }

    private void initData() {
        Intent intent = getIntent();
        mAction = intent.getIntExtra("action", Constant.ACTION_GENERATE_URL_QRCODEINFO);
        mQRcodeInfo = intent.getStringExtra("qrCode");
        mMapByte = intent.getByteArrayExtra("mapCode");
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
            case Constant.ACTION_GENERATE_URL_MODEL_QRCODEINFO:
                //首先让另外两个布局不可见
                mNameLl.setVisibility(View.GONE);
                mUrlRl.setVisibility(View.VISIBLE);
                mContentTv.setText(mQRcodeInfo);
                if (mMapByte != null) {
                    mMapBitmap = BitmapFactory.decodeByteArray(mMapByte, 0, mMapByte.length);
                }
                break;
            case Constant.ACTION_GENERATE_NAME_QRCODEINFO:
            case Constant.ACTION_GENERATE_NAME_MODEL_QRCODEINFO:
                //首先让另外两个布局不可见
                mUrlRl.setVisibility(View.GONE);
                mNameLl.setVisibility(View.VISIBLE);
                String mNameStr = intent.getStringExtra("mNameStr");
                String mPhoneStr = intent.getStringExtra("mPhoneStr");
                String mPosStr = intent.getStringExtra("mPosStr");
                String mPartStr = intent.getStringExtra("mPartStr");
                String mEmailStr = intent.getStringExtra("mEmailStr");
                String mCompanyStr = intent.getStringExtra("mCompanyStr");
                if (mMapByte != null) {
                    mMapBitmap = BitmapFactory.decodeByteArray(mMapByte, 0, mMapByte.length);
                }
                mNameTv.setText(mNameStr);
                mPhoneTv.setText(mPhoneStr);
                mPosTv.setText(mPosStr);
                mPartTv.setText(mPartStr);
                mEmailTv.setText(mEmailStr);
                mCompanyTv.setText(mCompanyStr);

                break;
            case Constant.ACTION_GENERATE_PHONE_QRCODEINFO:
            case Constant.ACTION_GENERATE_PHONE_MODEL_QRCODEINFO:
                mNameLl.setVisibility(View.GONE);
                mUrlRl.setVisibility(View.GONE);
                mPhoneRl.setVisibility(View.VISIBLE);
                mPhoneContentTv.setText(mQRcodeInfo);
                if (mMapByte != null) {
                    mMapBitmap = BitmapFactory.decodeByteArray(mMapByte, 0, mMapByte.length);
                }
                break;
            case Constant.ACTION_GENERATE_MAP_QRCODEINFO:
                mNameLl.setVisibility(View.GONE);
                mUrlRl.setVisibility(View.GONE);
                mPhoneRl.setVisibility(View.GONE);
                mMapLl.setVisibility(View.VISIBLE);
                mMapBitmap = BitmapFactory.decodeByteArray(mMapByte, 0, mMapByte.length);
                mShowMapIv.setImageBitmap(mMapBitmap);
                mMapContentTv.setText(mQRcodeInfo);
                break;
            case Constant.ACTION_GENERATE_MYCODE_QRCODEINFO:
                mNameLl.setVisibility(View.GONE);
                mUrlRl.setVisibility(View.VISIBLE);
                mContentTv.setText(mQRcodeInfo);
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

        mAccessToBt.setOnClickListener(this);
        mShareIv.setOnClickListener(this);
        mMapContentTv.setOnClickListener(this);

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
        mMyCodeTv.setVisibility(View.GONE);
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
            Bitmap qrCodeBitmap = EncodingHandler.createQRCode(mQRcodeInfo, 231);
            // saveBitmap(qrCodeBitmap);

            //------------------添加logo部分------------------//
            //Bitmap logoBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_invite);

            //二维码和logo合并
            Bitmap bitmap = Bitmap.createBitmap(qrCodeBitmap.getWidth(), qrCodeBitmap
                    .getHeight(), qrCodeBitmap.getConfig());
            drawBitmap(bitmap, mMapBitmap, qrCodeBitmap);
            //logo绘制在二维码中央
//            canvas.drawBitmap(logoBmp, qrCodeBitmap.getWidth() / 2
//                    - logoBmp.getWidth() / 2, qrCodeBitmap.getHeight()
//                    / 2 - logoBmp.getHeight() / 2, null);
            //------------------添加logo部分------------------//
            mQRcodeRl.setVisibility(View.VISIBLE);
            //分享用
            mBitmap = bitmap;
            mQRcodeBitmap.setImageBitmap(bitmap);
            ScaleAnimation animation = new ScaleAnimation(0.0f, 1.1f, 0.0f, 1.1f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(500);//设置动画持续时间
            animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
            mQRcodeRl.startAnimation(animation);
            mMyCodeTv.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(MakeResultActivity.this, "Text can not be empty", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 区分不同的action 画图
     *
     * @param bitmap
     * @param mMapBitmap
     * @param qrCodeBitmap
     */
    private void drawBitmap(Bitmap bitmap, Bitmap mMapBitmap, Bitmap qrCodeBitmap) {
        switch (mAction) {
            case Constant.ACTION_GENERATE_NAME_QRCODEINFO:
            case Constant.ACTION_GENERATE_PHONE_QRCODEINFO:
            case Constant.ACTION_GENERATE_URL_QRCODEINFO:
            case Constant.ACTION_GENERATE_MAP_QRCODEINFO:
                Canvas canvas = new Canvas(bitmap);
                //二维码
                canvas.drawBitmap(qrCodeBitmap, 0, 0, null);
                break;
            case Constant.ACTION_GENERATE_NAME_MODEL_QRCODEINFO:
            case Constant.ACTION_GENERATE_PHONE_MODEL_QRCODEINFO:
            case Constant.ACTION_GENERATE_URL_MODEL_QRCODEINFO:
            case Constant.ACTION_GENERATE_MAP_MODEL_QRCODEINFO:
                Utils.combineBitmap(bitmap, mMapBitmap, qrCodeBitmap);
                break;
            case Constant.ACTION_GENERATE_MYCODE_QRCODEINFO:
                Canvas myCodeCanvas = new Canvas(bitmap);
                //二维码
                myCodeCanvas.drawBitmap(qrCodeBitmap, 0, 0, null);
                break;
        }
    }

    /**
     * 将bitmap保存到本地
     */
    public void saveBitmap(Bitmap bitmap) {
        try {
            File f = new File("/sdcard/", "bitmap");
            if (f.exists()) {
                f.delete();
            } else {
                f.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.make_access_to:
                String url = mContentTv.getText().toString();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
                break;
            case R.id.to_right_img:
                Utils.showShare(Platform.SHARE_IMAGE, mBitmap);
                break;
            case R.id.map_content_tv:
                startShowLocationActivity();
                break;
        }
    }

    /**
     * 启动显示地理位置的界面
     */
    private void startShowLocationActivity() {
        Intent intent = new Intent(this, ShowLocationActivity.class);
        intent.putExtra("location", mMapContentTv.getText().toString());
        this.startActivity(intent);
    }
}
