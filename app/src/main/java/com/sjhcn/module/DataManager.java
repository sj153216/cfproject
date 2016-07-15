package com.sjhcn.module;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.sjhcn.application.QRcodeApplication;
import com.sjhcn.constants.Constant;
import com.sjhcn.db.QRcodeInfoIntf;
import com.sjhcn.db.QRcodeInfoManager;
import com.sjhcn.entitis.QRcodeInfo;
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
    private QRcodeInfoIntf codeInfoMgr;
    private Context mContext;
    private List<QRcodeInfo> codeInfoList;
    private LoadDataThread loadDataThread;

    Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int action = msg.what;
            Object obj = msg.obj;
            switch (action) {
                case Constant.ACTION_LOAD_QRCODEINFO:
                    codeInfoList = (List<QRcodeInfo>) obj;
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
        codeInfoMgr = QRcodeInfoManager.getInstance();
        codeInfoList = new ArrayList<QRcodeInfo>();
        loadDataThread = new LoadDataThread(mainHandler, codeInfoMgr);
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


    public void getDataFromLocal(LoadDataIntf loadDataIntf, int action) {
        this.loadDataIntf = loadDataIntf;
        if (loadDataIntf == null) {
            return;
        }
        this.loadDataIntf.onLoadStart();
        loadDataThread.addTask(action);
    }

    public List<QRcodeInfo> getCodeInfoList() {
        if (codeInfoList == null) {
            //确保activity拿数据的时候不为null，length为0可以，说明没有数据
            codeInfoList = new ArrayList<QRcodeInfo>();
        }
        return codeInfoList;
    }


}
