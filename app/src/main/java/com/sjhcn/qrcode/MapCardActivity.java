package com.sjhcn.qrcode;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.share.LocationShareURLOption;
import com.baidu.mapapi.search.share.OnGetShareUrlResultListener;
import com.baidu.mapapi.search.share.ShareUrlResult;
import com.baidu.mapapi.search.share.ShareUrlSearch;
import com.sjhcn.application.QRcodeApplication;
import com.sjhcn.constants.Constant;
import com.sjhcn.view.MyDialog;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by tong on 2016/7/15.
 */
public class MapCardActivity extends BaseActivity implements View.OnClickListener,
        OnGetShareUrlResultListener, OnGetGeoCoderResultListener {


    private TextView mTitle;
    private MapView mMapView;
    SDKReceiver mReceiver;
    private ImageView mShareIv;
    private ImageView mMakeQRcodeIv;
    private BaiduMap mBaiduMap;
    //地理位置管理
    private LocationManager locationManager;
    //位置提供器
    private String provider;
    private boolean isFirstLocate = true;

    private ImageView mNavigateIv;
    private BDLocation mLocation;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    private GeoCoder mGeoCoder;

    //分享
    private ShareUrlSearch mShareUrlSearch = null;
    //显示地理位置的dialog
    private MyDialog mDialog;
    private TextView mLatitudeTv;
    private TextView mLongitudeTv;
    private TextView mAddress;

    //视图切换
    private ImageView mPlusIv;
    private LinearLayout mPopupWindowLl;
    private TextView mNormalTv;
    private TextView mSateTv;

    //判断popupwindow是否已经显示
    private boolean isShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(QRcodeApplication.getInstance());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.map_card_activity);
        initView();
        initData();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTitle.setText("位置");
        mShareIv.setVisibility(View.VISIBLE);
        mMakeQRcodeIv.setVisibility(View.VISIBLE);
        mMapView.onResume();
    }

    private void initView() {
        mDialog = new MyDialog(this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_show_position);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mTitle = (TextView) findViewById(R.id.title_name);
        mShareIv = (ImageView) findViewById(R.id.to_right_img);
        mMakeQRcodeIv = (ImageView) findViewById(R.id.right_img);
        mMapView = (MapView) findViewById(R.id.map_view);
        mNavigateIv = (ImageView) findViewById(R.id.navigate_iv);
        mLatitudeTv = (TextView) mDialog.findViewById(R.id.latitude_tv);
        mLongitudeTv = (TextView) mDialog.findViewById(R.id.longitude_tv);
        mAddress = (TextView) mDialog.findViewById(R.id.dialog_location_tv);
        mPlusIv = (ImageView) findViewById(R.id.plus_iv);
        mPopupWindowLl = (LinearLayout) findViewById(R.id.popup_window);
        mNormalTv = (TextView) findViewById(R.id.map_normal_tv);
        mSateTv = (TextView) findViewById(R.id.map_sate_tv);

    }

    private void initData() {

        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        mReceiver = new SDKReceiver();
        registerReceiver(mReceiver, iFilter);
        mBaiduMap = mMapView.getMap();

        mShareUrlSearch = ShareUrlSearch.newInstance();
        mShareUrlSearch.setOnGetShareUrlResultListener(this);

        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(QRcodeApplication.getInstance());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
        mLocationClient.start();
    }

    private void initEvent() {
        mNavigateIv.setOnClickListener(this);
        mBaiduMap.setOnMapLongClickListener(new BaiduMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mLatitudeTv.setText(latLng.latitude + "");
                mLongitudeTv.setText(latLng.longitude + "");
                mAddress.setText(mLocation.getAddrStr());
                mDialog.show();
            }
        });
        mPlusIv.setOnClickListener(this);
        mNormalTv.setOnClickListener(this);
        mSateTv.setOnClickListener(this);
        mShareIv.setOnClickListener(this);
        mMakeQRcodeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
                    //地图截屏回调接口
                    public void onSnapshotReady(Bitmap snapshot) {
                        startMakeResultActivity(snapshot);
                    }
                });
            }
        });
    }

    /**
     * 打开制码结果页面
     *
     * @param snapshot
     */
    private void startMakeResultActivity(Bitmap snapshot) {
        Intent intent = new Intent(MapCardActivity.this, MakeResultActivity.class);
        ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
        snapshot.compress(Bitmap.CompressFormat.PNG, 100, output);//把bitmap100%高质量压缩 到 output对象里
        byte[] byteArray = output.toByteArray();//转换成功了
        intent.putExtra("mapCode", byteArray);
        intent.putExtra("action", Constant.ACTION_GENERATE_MAP_QRCODEINFO);
        intent.putExtra("qrCode", "坐标： " + mLocation.getLatitude() + ", " + mLocation.getLongitude() + ", " + mLocation.getAddrStr());
        MapCardActivity.this.startActivity(intent);
    }


    /**
     * 配置定位SDK参数
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(1000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 当不需要定位图层时关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mShareUrlSearch.destroy();
        unregisterReceiver(mReceiver);
    }


    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null || mMapView == null) {
                return;
            }
            //设成全局的，方便调用
            mLocation = location;
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude :  ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
                showMe();

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
                showMe();
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
        }

    }

    private void showMe() {
        if (isFirstLocate) {
            //LatLng ll = new LatLng(32.0797236 , 118.7665758);
            navigateTo();
            isFirstLocate = false;
        }
        // 构造定位数据
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(mLocation.getLatitude());
        builder.longitude(mLocation.getLongitude());
        builder.direction(100);
        MyLocationData locationData = builder.build();
        // 设置定位数据
        mBaiduMap.setMyLocationData(locationData);
//        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
//                .fromResource(R.drawable.location);
//        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
//        mBaiduMap.setMyLocationConfigeration(config);
    }


    /**
     * 定位到指定经纬度
     */
    private void navigateTo() {
        LatLng ll = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.navigate_iv:
                navigateTo();
                break;
            case R.id.plus_iv:
                showPopupWindow(mPopupWindowLl);
                break;
            case R.id.map_normal_tv:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.map_sate_tv:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.to_right_img:
                mShareUrlSearch.requestLocationShareUrl(new LocationShareURLOption().location(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()))
                        .name(mLocation.getAddrStr()+mLocation.getStreet()+mLocation.getStreetNumber()).snippet("我在这，你在哪"));
                break;
        }
    }

    /**
     * 显示PopupWindow用于切换地图视图，卫星之类的
     */
    private void showPopupWindow(View v) {
        if (isShowing) {
            setAnimator(v, -200f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f);
            mPopupWindowLl.setVisibility(View.GONE);
            isShowing = false;
        } else {
            mPopupWindowLl.setVisibility(View.VISIBLE);
            setAnimator(v, 200f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f);
            isShowing = true;
        }
    }

    /**
     * 设置动画
     *
     * @param v
     * @param tran
     * @param x
     * @param xTo
     * @param y
     * @param yTo
     * @param a
     * @param aTo
     */
    private void setAnimator(View v, float tran, float x, float xTo, float y, float yTo, float a, float aTo) {
        ObjectAnimator trans = ObjectAnimator.ofFloat(v, "translationY", tran);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", x, xTo);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", y, yTo);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", a, aTo);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(200);
        set.playTogether(trans, scaleX, scaleY, alpha);
        set.start();
    }

    /**
     * 验证key权限
     */
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

    @Override
    public void onGetPoiDetailShareUrlResult(ShareUrlResult shareUrlResult) {
        // 分享短串结果
        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_TEXT, "您的朋友通过百度地图SDK与您分享一个POI点详情: "
                + " -- " + shareUrlResult.getUrl());
        it.setType("text/plain");
        startActivity(Intent.createChooser(it, "将短串分享到"));
    }

    //分享接口

    @Override
    public void onGetLocationShareUrlResult(ShareUrlResult shareUrlResult) {
        // 分享短串结果
        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_TEXT, "您的朋友通过百度地图SDK与您分享一个位置: "
                + " -- " + shareUrlResult.getUrl());
        it.setType("text/plain");
        startActivity(Intent.createChooser(it, "将短串分享到"));

    }

    // 编码接口

    /**
     * 正向编码
     *
     * @param geoCodeResult
     */
    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        if (geoCodeResult == null
                || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            // 没有检测到结果
        }
    }

    /**
     * 反向编码
     *
     * @param reverseGeoCodeResult
     */
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        if (reverseGeoCodeResult == null
                || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            // 没有检测到结果
            Toast.makeText(MapCardActivity.this, "抱歉，未能找到结果",
                    Toast.LENGTH_LONG).show();
        }
        Toast.makeText(MapCardActivity.this,
                "位置：" + reverseGeoCodeResult.getAddress(), Toast.LENGTH_LONG)
                .show();
    }


}



