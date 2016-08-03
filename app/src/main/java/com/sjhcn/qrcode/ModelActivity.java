package com.sjhcn.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjhcn.application.QRcodeApplication;
import com.sjhcn.constants.Constant;
import com.sjhcn.recyclerview_adapter.ModelGridViewAdapter;
import com.sjhcn.recyclerview_adapter.MyPagerAdapter;
import com.sjhcn.view.ViewPagerIndicator;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjhcn on 2016/8/20.
 */
public class ModelActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextView mTitle;
    private Button mSelectBt;
    //namecard生成二维码的字符串
    private String mQRcodeStr;
    private String mNameStr;
    private String mPhoneStr;
    private String mPosStr;
    private String mPartStr;
    private String mEmailStr;
    private String mCompanyStr;
    //phoneCard界面
    private String mQRPhoneStr;
    //传递过来的action
    private int action;
    //viewPager部分
    private ViewPager mViewPager;
    private MyPagerAdapter mPagerAdapter;
    //经典
    private GridView mClassGv;
    //黄昏
    private GridView mSunsetGv;
    //青春
    private GridView mSunshineGv;
    //存放GridView的list
    private ArrayList<View> mViewList = new ArrayList<View>(3);

    private int[] mBitmaps = new int[]{R.drawable.gexing_2, R.drawable.gexing_3, R.drawable.gexing_4,
            R.drawable.gexing_5, R.drawable.gexing_8, R.drawable.gexing_9,
            R.drawable.gexing_10, R.drawable.gexing_12};
    private int[] mClassBitmaps = new int[]{R.drawable.class_1, R.drawable.class_2, R.drawable.class_3,
            R.drawable.class_4, R.drawable.class_5, R.drawable.class_6,
            R.drawable.class_7, R.drawable.class_8};
    private List<Drawable> mData = new ArrayList<Drawable>();
    private List<Drawable> mClassData = new ArrayList<Drawable>();
    //三个gridview的adapter，只不过数据集不同
    private ModelGridViewAdapter mClassAdapter;
    private ModelGridViewAdapter mSunsetAdapter;
    private ModelGridViewAdapter mSunshineAdapter;

    private ViewPagerIndicator mIndicator;

    private int lastClickPos = 0;
    private boolean isSelected = false;
    public static Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.model_activity);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.title_name);
        mSelectBt = (Button) findViewById(R.id.model_bt);
        mViewPager = (ViewPager) findViewById(R.id.indicator_viewpager);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);
    }

    private void initData() {
        Intent intent = getIntent();
        mNameStr = intent.getStringExtra("mNameStr");
        mPhoneStr = intent.getStringExtra("mPhoneStr");
        mPosStr = intent.getStringExtra("mPosStr");
        mPartStr = intent.getStringExtra("mPartStr");
        mEmailStr = intent.getStringExtra("mEmailStr");
        mCompanyStr = intent.getStringExtra("mCompanyStr");
        mQRcodeStr = intent.getStringExtra("qrCode");
        action = intent.getIntExtra("action", Constant.ACTION_GENERATE_MAP_QRCODEINFO);

        initGridView();
        for (int i = 0; i < mBitmaps.length; i++) {
            mData.add(getResources().getDrawable(mBitmaps[i]));
        }
        for (int i = 0; i < mClassBitmaps.length; i++) {
            mClassData.add(getResources().getDrawable(mClassBitmaps[i]));
        }
        mPagerAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(mPagerAdapter);

        mClassAdapter = new ModelGridViewAdapter(mData, this);
        mClassGv.setAdapter(mClassAdapter);
        mSunsetAdapter = new ModelGridViewAdapter(mData, this);
        mSunsetGv.setAdapter(mSunsetAdapter);
        mSunshineAdapter = new ModelGridViewAdapter(mClassData, this);
        mSunshineGv.setAdapter(mSunshineAdapter);
        mIndicator.setViewPager(mViewPager, 0);


    }

    /**
     * 初始化GridView
     */
    private void initGridView() {
        mClassGv = new GridView(this);
        mClassGv.setHorizontalSpacing(2);
        mClassGv.setVerticalSpacing(2);
        mClassGv.setNumColumns(3);
        mViewList.add(mClassGv);

        mSunshineGv = new GridView(this);
        mSunshineGv.setHorizontalSpacing(2);
        mSunshineGv.setVerticalSpacing(2);
        mSunshineGv.setNumColumns(3);
        mViewList.add(mSunshineGv);

        mSunsetGv = new GridView(this);
        mSunsetGv.setHorizontalSpacing(2);
        mSunsetGv.setVerticalSpacing(2);
        mSunsetGv.setNumColumns(3);
        mViewList.add(mSunsetGv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTitle.setText("选择背景");
    }

    private void initEvent() {
        mSelectBt.setOnClickListener(this);
        mClassGv.setOnItemClickListener(this);
        mSunshineGv.setOnItemClickListener(this);
        mSunsetGv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.model_bt:
                startMakeResuleActivity();
                break;
        }
    }

    /**
     * 启动界面
     */
    private void startMakeResuleActivity() {
        Intent intent = new Intent(this, MakeResultActivity.class);
        switch (action) {
            case Constant.ACTION_GENERATE_NAME_QRCODEINFO:
                intent.putExtra("mNameStr", mNameStr);
                intent.putExtra("mPhoneStr", mPhoneStr);
                intent.putExtra("mPosStr", mPosStr);
                intent.putExtra("mPartStr", mPartStr);
                intent.putExtra("mEmailStr", mEmailStr);
                intent.putExtra("mCompanyStr", mCompanyStr);
                intent.putExtra("qrCode", mQRcodeStr);
                intent.putExtra("mapCode", compressBitmap(mBitmap));
                intent.putExtra("action", Constant.ACTION_GENERATE_NAME_MODEL_QRCODEINFO);
                break;
            case Constant.ACTION_GENERATE_PHONE_QRCODEINFO:
                intent.putExtra("qrCode", mQRcodeStr);
                intent.putExtra("action", Constant.ACTION_GENERATE_PHONE_QRCODEINFO);
                break;
            case Constant.ACTION_GENERATE_URL_QRCODEINFO:
                intent.putExtra("qrCode", mQRcodeStr);
                intent.putExtra("action", Constant.ACTION_GENERATE_URL_MODEL_QRCODEINFO);
                break;
            case Constant.ACTION_GENERATE_MAP_QRCODEINFO:

                break;
        }
        startActivity(intent);
    }

    /**
     * 将bitmap转为byte【】
     *
     * @param bitmap
     * @return
     */
    private byte[] compressBitmap(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] array = outputStream.toByteArray();
        return array;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView selectIv = (ImageView) view.findViewById(R.id.model_item_select_iv);
        if (lastClickPos == position) {
            // ImageView iv = (ImageView) view.findViewById(R.id.model_item_iv);
            //若果是，判断是否已经选择过
            if (!isSelected) {
                selectIv.setVisibility(View.VISIBLE);
                isSelected = true;
                mBitmap = BitmapFactory.decodeResource(QRcodeApplication.getInstance().getResources(), mBitmaps[position]);
            } else {
                selectIv.setVisibility(View.GONE);
                isSelected = false;
            }
        } else {
            if (isSelected) {
                int count = mClassGv.getChildCount();
                ImageView oldSelectIv = (ImageView) mClassGv.getChildAt(lastClickPos).findViewById(R.id.model_item_select_iv);
                if (oldSelectIv != null) {
                    oldSelectIv.setVisibility(View.GONE);
                }
            }
            selectIv.setVisibility(View.VISIBLE);
            isSelected = true;
            mBitmap = BitmapFactory.decodeResource(QRcodeApplication.getInstance().getResources(), mBitmaps[position]);
        }
        lastClickPos = position;
    }
}
