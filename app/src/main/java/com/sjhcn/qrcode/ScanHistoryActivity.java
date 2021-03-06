package com.sjhcn.qrcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sjhcn.constants.Constant;
import com.sjhcn.entitis.MakeRecordItem;
import com.sjhcn.entitis.QRcodeMakeInfo;
import com.sjhcn.entitis.QRcodeScanInfo;
import com.sjhcn.entitis.ScanRecordItem;
import com.sjhcn.module.DataManager;
import com.sjhcn.recyclerview_adapter.MakeRecordAdapter;
import com.sjhcn.recyclerview_adapter.MyPagerAdapter;
import com.sjhcn.recyclerview_adapter.ScanRecordAdapter;
import com.sjhcn.utils.MyDateUtils;
import com.sjhcn.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong on 2016/7/13.
 */
public class ScanHistoryActivity extends BaseActivity implements View.OnClickListener {

    private static final int POSITION_SCAN_RECORD = 0;
    private static final int POSITION_MAKE_RECORD = 1;
    private TextView mScanRecordTv;
    private TextView mMakeRecordTv;
    private TextView mTitle;

    private MyPagerAdapter pagerAdapter;

    private ScanRecordAdapter mScanRecyclerViewAdapter;
    private MakeRecordAdapter mMakeRecyclerViewAdapter;
    private List<ScanRecordItem> mScanDatas;
    private List<MakeRecordItem> mMakeDatas;
    private DataManager mDataMgr;

    //保存从数据库获取的scanCodeInfoList
    private List<QRcodeScanInfo> scanCodeInfoList;
    //保存从数据库获取的makeCodeInfoList
    private List<QRcodeMakeInfo> makeCodeInfoList;


    private ViewPager mViewPager;
    private RecyclerView mScanRecord;
    private RecyclerView mMakeRecord;
    private ArrayList<View> mViewList = new ArrayList<View>(2);

    private LinearLayout mNoFootLl;
    private TextView mNoFootTv;


    private Bitmap urlBitmap;
    private Bitmap normalBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scan_history_activity);
        initView();
        initData();
        initEvent();
    }


    private void initView() {
        mScanRecordTv = (TextView) findViewById(R.id.scan_record);
        mMakeRecordTv = (TextView) findViewById(R.id.make_record);
        mTitle = (TextView) findViewById(R.id.title_name);
        mViewPager = (ViewPager) findViewById(R.id.history_viewpager);
        mScanRecord = new RecyclerView(this);
        initRecyclerView(mScanRecord);
        mMakeRecord = new RecyclerView(this);
        initRecyclerView(mMakeRecord);
        mViewList.add(POSITION_SCAN_RECORD, mScanRecord);
        mViewList.add(POSITION_MAKE_RECORD, mMakeRecord);
        mNoFootLl = (LinearLayout) findViewById(R.id.nofoot_ll);
        mNoFootTv = (TextView) findViewById(R.id.record_tv);
    }

    private void initData() {
        // mScanRecordTv.setBackgroundColor(Color.parseColor("#66DDFB"));
        urlBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ie);
        normalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.contace_circle);
        mTitle.setText("历史记录");
        mDataMgr = DataManager.getInstance();
        scanCodeInfoList = mDataMgr.getScanCodeInfoList();
        makeCodeInfoList = mDataMgr.getMakeCodeInfoList();
        mScanDatas = new ArrayList<ScanRecordItem>();
        mMakeDatas = new ArrayList<MakeRecordItem>();

        fillScanItem(scanCodeInfoList);
        fillMakeItem(makeCodeInfoList);
        pagerAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(pagerAdapter);
        mScanRecyclerViewAdapter = new ScanRecordAdapter(this, mScanDatas);
        mMakeRecyclerViewAdapter = new MakeRecordAdapter(this, mMakeDatas);
        mScanRecord.setAdapter(mScanRecyclerViewAdapter);
        mMakeRecord.setAdapter(mMakeRecyclerViewAdapter);

    }

    /**
     * 因为从数据库取出的数据是qrcode，要转换成item设置给adapter
     *
     * @param codeInfoList
     */
    private void fillScanItem(List<QRcodeScanInfo> codeInfoList) {
        if (codeInfoList.size() > 0) {
            for (int i = 0; i < codeInfoList.size(); i++) {
                ScanRecordItem recordItem = new ScanRecordItem();
                QRcodeScanInfo codeInfo = codeInfoList.get(i);
                if (codeInfo.getQRcodeType() == Constant.SCAN_QRCODE_YTPE_URL) {
                    recordItem.setLable(urlBitmap);
                } else {
                    recordItem.setLable(normalBitmap);
                }
                recordItem.setArrow(BitmapFactory.decodeResource(getResources(), R.drawable.ic_find_previous_holo_dark));
                recordItem.setQrcode(codeInfo.getQRcode());
                recordItem.setTime(MyDateUtils.formatYearMonthDay(codeInfo.getScanTime(), "-") + "  " +
                        MyDateUtils.formatHourMin(codeInfo.getScanTime(), ":"));
                mScanDatas.add(recordItem);

            }
        }

    }

    /**
     * 因为从数据库取出的数据是qrcode，要转换成item设置给adapter
     *
     * @param codeInfoList
     */
    private void fillMakeItem(List<QRcodeMakeInfo> codeInfoList) {
        if (codeInfoList.size() > 0) {
            for (int i = 0; i < codeInfoList.size(); i++) {
                MakeRecordItem recordItem = new MakeRecordItem();
                QRcodeMakeInfo codeInfo = codeInfoList.get(i);
                if (codeInfo.getQRcodeType() == Constant.MAKE_QRCODE_YTPE_URL) {
                    recordItem.setLable(urlBitmap);
                } else if (codeInfo.getQRcodeType() == Constant.MAKE_QRCODE_YTPE_NORMAL) {
                    recordItem.setLable(normalBitmap);
                } else if (codeInfo.getQRcodeType() == Constant.MAKE_QRCODE_YTPE_MAP) {
                    //地图暂未做
                }
                recordItem.setArrow(BitmapFactory.decodeResource(getResources(), R.drawable.ic_find_previous_holo_dark));
                recordItem.setQrcode(codeInfo.getQRcode());
                recordItem.setTime(MyDateUtils.formatYearMonthDay(codeInfo.getMakeTime(), "-") + "  " +
                        MyDateUtils.formatHourMin(codeInfo.getMakeTime(), ":"));
                mMakeDatas.add(recordItem);

            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mScanDatas.size() == 0) {
            mNoFootLl.setVisibility(View.VISIBLE);
            mNoFootTv.setText("还没有扫码记录哦");
        }
    }

    /**
     * 初始化recyclerView
     *
     * @param recyclerView
     */
    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.HORIZONTAL_LIST));

    }

    private void initEvent() {
        mScanRecordTv.setOnClickListener(this);
        mMakeRecordTv.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        if (mScanDatas.size() == 0) {
                            mNoFootLl.setVisibility(View.VISIBLE);
                            mNoFootTv.setText("还没有扫码记录哦");
                            showScanRecyclerView();
                        } else {
                            //mScanRecord.setAdapter(mScanRecyclerViewAdapter);
                            showScanRecyclerView();
                            mNoFootLl.setVisibility(View.GONE);
                        }
                        break;
                    case 1:
                        if (mMakeDatas.size() == 0) {
                            mNoFootLl.setVisibility(View.VISIBLE);
                            mNoFootTv.setText("还没有制码记录哦");
                            showMakeRecyclerView();
                        } else {
                            // mMakeRecord.setAdapter(mMakeRecyclerViewAdapter);
                            mNoFootLl.setVisibility(View.GONE);
                            showMakeRecyclerView();
                        }
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan_record:
                if (mScanDatas.size() == 0) {
                    mNoFootLl.setVisibility(View.VISIBLE);
                    mNoFootTv.setText("还没有扫码记录哦");

                    showScanRecyclerView();
                } else {
                    // mScanRecord.setAdapter(mScanRecyclerViewAdapter);
                    showScanRecyclerView();
                    mNoFootLl.setVisibility(View.GONE);
                }
                break;
            case R.id.make_record:
                if (mMakeDatas.size() == 0) {
                    mNoFootLl.setVisibility(View.VISIBLE);
                    mNoFootTv.setText("还没有制骂记录哦");
                    showMakeRecyclerView();
                } else {
                    //  mMakeRecord.setAdapter(mMakeRecyclerViewAdapter);
                    showMakeRecyclerView();
                    mNoFootLl.setVisibility(View.GONE);
                }
                break;
        }
    }

    /**
     * 当viewpager滑动时，选择make textview
     */
    private void showMakeRecyclerView() {
        // mScanRecordTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mScanRecordTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_corner_make_history));
        //mMakeRecordTv.setBackgroundColor(Color.parseColor("#66DDFB"));
        mMakeRecordTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_corner_scan_history));
        mViewPager.setCurrentItem(POSITION_MAKE_RECORD);
    }

    /**
     * 当viewpager滑动时，选择scan textview
     */
    private void showScanRecyclerView() {
        // mScanRecordTv.setBackgroundColor(Color.parseColor("#66DDFB"));
        mScanRecordTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_corner_scan_history));
        //mMakeRecordTv.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mMakeRecordTv.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_corner_make_history));
        mViewPager.setCurrentItem(POSITION_SCAN_RECORD);
    }

}
