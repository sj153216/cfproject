package com.sjhcn.module;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.sjhcn.application.QRcodeApplication;
import com.sjhcn.constants.Constant;
import com.sjhcn.db.QRcodeMakeInfoIntf;
import com.sjhcn.db.QRcodeMakeInfoManager;
import com.sjhcn.db.QRcodeScanInfoIntf;
import com.sjhcn.db.QRcodeScanInfoManager;
import com.sjhcn.entitis.QRcodeMakeInfo;
import com.sjhcn.entitis.QRcodeScanInfo;
import com.sjhcn.intf.LoadDataIntf;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据管理类
 * Created by sjhcn on 2016/7/16.
 */
public class DataManager {


    private static DataManager mInstance = null;

    private LoadDataIntf loadDataIntf = null;
    private QRcodeScanInfoIntf scanCodeInfoMgr;
    private QRcodeMakeInfoIntf makeCodeInfoMgr;
    private Context mContext;
    //存放扫描的的二维码信息
    private List<QRcodeScanInfo> scanCodeInfoList;
    //存放制码的二维码信息
    private List<QRcodeMakeInfo> makeCodeInfoList;
    private LoadDataThread loadDataThread;

    Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int action = msg.what;
            Object obj = msg.obj;
            switch (action) {
                case Constant.ACTION_LOAD_SCAN_QRCODEINFO:
                    scanCodeInfoList = (List<QRcodeScanInfo>) obj;
                    loadDataIntf.onLoadFinish(action);
                    break;
                case Constant.ACTION_LOAD_MAKE_QRCODEINFO:
                    makeCodeInfoList = (List<QRcodeMakeInfo>) obj;
                    loadDataIntf.onLoadFinish(action);
                    break;
                default:
                    break;
            }
        }

    };

    private DataManager() {

    }

    private void init() {
        mContext = QRcodeApplication.getInstance();
        scanCodeInfoMgr = QRcodeScanInfoManager.getInstance();
        makeCodeInfoMgr = QRcodeMakeInfoManager.getInstance();
        scanCodeInfoList = new ArrayList<QRcodeScanInfo>();
        makeCodeInfoList = new ArrayList<QRcodeMakeInfo>();
        loadDataThread = new LoadDataThread(mainHandler, scanCodeInfoMgr, makeCodeInfoMgr);
        loadDataThread.start();
    }

    /**
     * DCL单例模式
     *
     * @return
     */
    public static DataManager getInstance() {
        if (mInstance == null) {
            synchronized ("") {
                if (mInstance == null) {
                    mInstance = new DataManager();
                    mInstance.init();
                }
            }
        }
        return mInstance;
    }


    /**
     * 从本地数据库加载数据
     *
     * @param loadDataIntf
     * @param action
     */
    public void getDataFromLocal(LoadDataIntf loadDataIntf, int action) {
        this.loadDataIntf = loadDataIntf;
        if (loadDataIntf == null) {
            return;
        }
        if (action == Constant.ACTION_LOAD_MAKE_QRCODEINFO) {
            this.loadDataIntf.onLoadStart();
        }
        loadDataThread.addTask(action);
    }


    /**
     * 拿到扫描的二维码数据
     *
     * @return
     */
    public List<QRcodeScanInfo> getScanCodeInfoList() {
        if (scanCodeInfoList == null) {
            //确保activity拿数据的时候不为null，length为0可以，说明没有数据
            scanCodeInfoList = new ArrayList<QRcodeScanInfo>();
        }
        return scanCodeInfoList;
    }

    /**
     * 拿到制码的二维码数据
     *
     * @return
     */
    public List<QRcodeMakeInfo> getMakeCodeInfoList() {
        if (makeCodeInfoList == null) {
            //确保activity拿数据的时候不为null，length为0可以，说明没有数据
            makeCodeInfoList = new ArrayList<QRcodeMakeInfo>();
        }
        return makeCodeInfoList;
    }


}
