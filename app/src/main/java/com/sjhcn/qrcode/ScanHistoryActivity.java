package com.sjhcn.qrcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjhcn.constants.Constant;
import com.sjhcn.entitis.QRcodeInfo;
import com.sjhcn.entitis.RecordItem;
import com.sjhcn.module.DataManager;
import com.sjhcn.recyclerview_adapter.ScanRecordAdapter;
import com.sjhcn.utils.MyDateUtils;
import com.sjhcn.view.DividerItemDecoration;
import com.sjhcn.view.MyTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong on 2016/7/13.
 */
public class ScanHistoryActivity extends BaseActivity {

    private static final int POSITION_SCAN_RECORD = 0;
    private static final int POSITION_MAKE_RECORD = 1;
    private MyTextView mScanRecordTv;
    private MyTextView mMakeRecordTv;
    private TextView mTitle;

    private MyPagerAdapter pagerAdapter;

    private ScanRecordAdapter mRecyclerViewAdapter;
    private List<RecordItem> mDatas;
    private DataManager mDataMgr;

    //保存从数据库获取的codeInfoList
    private List<QRcodeInfo> codeInfoList;


    private ViewPager mViewPager;
    private RecyclerView mScanRecord;
    private RecyclerView mMakeRecord;
    private ArrayList<View> mViewList = new ArrayList<View>(2);
    ;


    private Bitmap urlBitmap;
    private Bitmap normalBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_history_activity);
        initView();
        initData();
    }

    private void initView() {
        mScanRecordTv = (MyTextView) findViewById(R.id.scan_record);
        mMakeRecordTv = (MyTextView) findViewById(R.id.make_record);
        mTitle = (TextView) findViewById(R.id.title_name);
        mViewPager = (ViewPager) findViewById(R.id.history_viewpager);
        mScanRecord = new RecyclerView(this);
        initRecyclerView(mScanRecord);
        mMakeRecord = new RecyclerView(this);
        initRecyclerView(mMakeRecord);
        mViewList.add(POSITION_SCAN_RECORD, mScanRecord);
        mViewList.add(POSITION_MAKE_RECORD, mMakeRecord);
    }

    private void initData() {
        urlBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_add);
        normalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_invite);
        mTitle.setText("历史记录");
        mDataMgr = DataManager.getInstance();
        codeInfoList = mDataMgr.getCodeInfoList();
        mDatas = new ArrayList<RecordItem>();
        fillItem(codeInfoList);
        pagerAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(pagerAdapter);
        mRecyclerViewAdapter = new ScanRecordAdapter(this, mDatas);
        mScanRecord.setAdapter(mRecyclerViewAdapter);

    }

    /**
     * 因为从数据库取出的数据是qrcode，要转换成item设置给adapter
     *
     * @param codeInfoList
     */
    private void fillItem(List<QRcodeInfo> codeInfoList) {
        if (codeInfoList.size() > 0) {
            for (int i = 0; i < codeInfoList.size(); i++) {
                RecordItem recordItem = new RecordItem();
                QRcodeInfo codeInfo = codeInfoList.get(i);
                if (codeInfo.getQRcodeType() == Constant.QRCODE_YTPE_URL) {
                    recordItem.setLable(urlBitmap);
                } else {
                    recordItem.setLable(normalBitmap);
                }
                recordItem.setArrow(BitmapFactory.decodeResource(getResources(), R.drawable.ic_find_previous_holo_dark));
                recordItem.setQrcode(codeInfo.getQRcode());
                recordItem.setTime(MyDateUtils.formatYearMonthDay(codeInfo.getScanTime(), "-") + "  " +
                        MyDateUtils.formatHourMin(codeInfo.getScanTime(), ":"));
                mDatas.add(recordItem);

            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

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

    // ViewPager适配器
    class MyPagerAdapter extends PagerAdapter {
        private ArrayList<View> mViewList;

        public MyPagerAdapter(ArrayList<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();// 页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;// 官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));// 添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));// 删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";// 页卡标题
        }

    }
}
