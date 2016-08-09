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
public class ModelActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, ViewPagerIndicator.OnPageChangeListener {

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

    private int[] mSunshineBitmaps = new int[]{R.drawable.gexing_2, R.drawable.gexing_3, R.drawable.gexing_4,
            R.drawable.gexing_5, R.drawable.gexing_8, R.drawable.gexing_9,
            R.drawable.gexing_10, R.drawable.gexing_12};
    private int[] mClassBitmaps = new int[]{R.drawable.class_1, R.drawable.class_2, R.drawable.class_3,
            R.drawable.class_4, R.drawable.class_5, R.drawable.class_6,
            R.drawable.class_7, R.drawable.class_8};
    private int[] mSunsetBitmaps = new int[]{R.drawable.set_1, R.drawable.set_2, R.drawable.set_3,
            R.drawable.set_4, R.drawable.set_5, R.drawable.set_6,
            R.drawable.set_7, R.drawable.set_8, R.drawable.set_8};
    private List<Drawable> mClassData = new ArrayList<Drawable>();
    private List<Drawable> mSunshineData = new ArrayList<Drawable>();
    private List<Drawable> mSunsetData = new ArrayList<Drawable>();
    //三个gridview的adapter，只不过数据集不同
    private ModelGridViewAdapter mClassAdapter;
    private ModelGridViewAdapter mSunsetAdapter;
    private ModelGridViewAdapter mSunshineAdapter;

    private ViewPagerIndicator mIndicator;

    private int lastClickPos = 0;
    private boolean isSelected = false;
    public Bitmap mBitmap;
    private int mViewPagerCurrentPos = 0;

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
        for (int i = 0; i < mClassBitmaps.length; i++) {
            mClassData.add(getResources().getDrawable(mClassBitmaps[i]));
        }
        for (int i = 0; i < mSunsetBitmaps.length; i++) {
            mSunsetData.add(getResources().getDrawable(mSunsetBitmaps[i]));
        }
        for (int i = 0; i < mSunshineBitmaps.length; i++) {
            mSunshineData.add(getResources().getDrawable(mSunshineBitmaps[i]));
        }
        mPagerAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(mPagerAdapter);

        mClassAdapter = new ModelGridViewAdapter(mClassData, this);
        mClassGv.setAdapter(mClassAdapter);
        mSunsetAdapter = new ModelGridViewAdapter(mSunsetData, this);
        mSunsetGv.setAdapter(mSunsetAdapter);
        mSunshineAdapter = new ModelGridViewAdapter(mSunshineData, this);
        mSunshineGv.setAdapter(mSunshineAdapter);
        mIndicator.setViewPager(mViewPager, 0);


    }

    /**
     * 初始化GridView
     */
    private void initGridView() {
        mClassGv = new GridView(this);
        mClassGv.setHorizontalSpacing(3);
        mClassGv.setVerticalSpacing(3);
        mClassGv.setNumColumns(3);
        mViewList.add(mClassGv);

        mSunsetGv = new GridView(this);
        mSunsetGv.setHorizontalSpacing(3);
        mSunsetGv.setVerticalSpacing(3);
        mSunsetGv.setNumColumns(3);
        mViewList.add(mSunsetGv);

        mSunshineGv = new GridView(this);
        mSunshineGv.setHorizontalSpacing(3);
        mSunshineGv.setVerticalSpacing(3);
        mSunshineGv.setNumColumns(3);
        mViewList.add(mSunshineGv);
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
        mIndicator.setOnPagerChangerListener(this);
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
                intent.putExtra("mapCode", compressBitmap(mBitmap));
                intent.putExtra("action", Constant.ACTION_GENERATE_PHONE_MODEL_QRCODEINFO);
                break;
            case Constant.ACTION_GENERATE_URL_QRCODEINFO:
                intent.putExtra("qrCode", mQRcodeStr);
                intent.putExtra("mapCode", compressBitmap(mBitmap));
                intent.putExtra("action", Constant.ACTION_GENERATE_URL_MODEL_QRCODEINFO);
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
        GridView temp = null;
        switch (mViewPagerCurrentPos) {
            case 0:
                temp = mClassGv;
                break;
            case 1:
                temp = mSunsetGv;
                break;
            case 2:
                temp = mSunshineGv;
                break;
        }
        for (int i = 0; i < temp.getChildCount(); i++) {
            ImageView oldSelectIv = (ImageView) temp.getChildAt(i).findViewById(R.id.model_item_select_iv);
            oldSelectIv.setVisibility(View.GONE);
        }
        ImageView selectIv = (ImageView) view.findViewById(R.id.model_item_select_iv);
        if (lastClickPos == position) {
            // ImageView iv = (ImageView) view.findViewById(R.id.model_item_iv);
            //若果是，判断是否已经选择过
            if (!isSelected) {
                selectIv.setVisibility(View.VISIBLE);
                isSelected = true;
                currentGridView(mViewPagerCurrentPos, position);
            } else {
                selectIv.setVisibility(View.GONE);
                isSelected = false;
            }
        } else {
            if (isSelected) {
                ImageView oldSelectIv = (ImageView) temp.getChildAt(lastClickPos).findViewById(R.id.model_item_select_iv);
                if (oldSelectIv != null) {
                    oldSelectIv.setVisibility(View.GONE);
                }
            }
            selectIv.setVisibility(View.VISIBLE);
            isSelected = true;
            currentGridView(mViewPagerCurrentPos, position);
        }
        lastClickPos = position;
    }

    /**
     * 判断当前在哪个gridview
     */
    private void currentGridView(int viewPagerPos, int itemPos) {
        switch (viewPagerPos) {
            case 0:
                mBitmap = BitmapFactory.decodeResource(QRcodeApplication.getInstance().getResources(), mClassBitmaps[itemPos]);
                break;
            case 1:
                mBitmap = BitmapFactory.decodeResource(QRcodeApplication.getInstance().getResources(), mSunsetBitmaps[itemPos]);
                break;
            case 2:
                mBitmap = BitmapFactory.decodeResource(QRcodeApplication.getInstance().getResources(), mSunshineBitmaps[itemPos]);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //当界面滑动时，将lastClickPos置0
        lastClickPos = 0;
        isSelected = false;
    }

    @Override
    public void onPageSelected(int position) {
        mViewPagerCurrentPos = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
