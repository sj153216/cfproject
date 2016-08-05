package com.sjhcn.qrcode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.sjhcn.application.QRcodeApplication;

import java.util.List;

/**
 * Created by tong on 2016/7/15.
 */
public class MapCardActivity extends BaseActivity {


    private TextView mTitle;
    private MapView mMapView;
    SDKReceiver mReceiver;
    private BaiduMap mBaiduMap;
    //地理位置管理
    private LocationManager locationManager;
    //位置提供器
    private String provider;
    private boolean isFirstLocate = true;

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
        locationManager.requestLocationUpdates("gps", 60000, 1, locationListener);
    }


    private void initView() {
        mTitle = (TextView) findViewById(R.id.title_name);
        mMapView = (MapView) findViewById(R.id.map_view);

    }

    private void initData() {
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new SDKReceiver();
        registerReceiver(mReceiver, iFilter);
        mBaiduMap = mMapView.getMap();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(MapCardActivity.this, "当前没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            navigateTo(location);
        }
        locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
    }

    /**
     * 定位到指定的经纬度
     *
     * @param location
     */
    private void navigateTo(Location location) {
        if (isFirstLocate) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            mBaiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //更新档前位置信息
            if (location != null) {

                navigateTo(location);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        unregisterReceiver(mReceiver);
        if (locationManager != null) {
            //程序关闭时将监听器移除
            locationManager.removeUpdates(locationListener);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }


    private void initEvent() {

    }

    class SDKReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
                // key 验证失败，相应处理
                Toast.makeText(MapCardActivity.this, "quan xian cuo wu", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



