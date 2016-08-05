package com.sjhcn.qrcode;

import android.os.Bundle;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.sjhcn.application.QRcodeApplication;
import com.sjhcn.intf.LoadDataIntf;

/**
 * Created by tong on 2016/7/15.
 */
public class MapCardActivity extends BaseActivity implements LoadDataIntf {


    private TextView mTitle;
    private MapView mMapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(QRcodeApplication.getInstance());
        setContentView(R.layout.map_card_activity);
        initView();
        initData();
        initEvent();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mTitle.setText("位置");
        mMapView.onResume();
    }


    private void initView() {
        mTitle = (TextView) findViewById(R.id.title_name);
        mMapView = (MapView) findViewById(R.id.map_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    private void initData() {

    }


    private void initEvent() {

    }


    @Override
    public void onLoadFinish(int action) {

    }

    @Override
    public void onLoadStart() {

    }
}
