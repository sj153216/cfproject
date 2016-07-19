package com.sjhcn.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.sjhcn.constants.Constant;
import com.sjhcn.db.DBHelper;
import com.sjhcn.db.QRcodeInfoManager;
import com.sjhcn.entitis.QRcodeInfo;

/**
 * Created by sjhcn on 2016/7/16.
 */
public class HandleQRcodeService extends Service {

    public static final String ACTION_SAVE_TO_LOCAL = "save_to_local";
    public static final String ACTION_GET_FROM_LOCAL = "get_from_local";
    private String result = null;
    private String mAction = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            stopSelf();
            return START_NOT_STICKY;
        }
        mAction = intent.getAction();
        result = intent.getStringExtra("serviceResult");
        new OperateDataThread().start();
        return START_STICKY;
    }

    /**
     * 根据传进来的action操作数据
     *
     * @param action
     */
    private void handleQRcodeInfo(String action) {
        if (action.equals(ACTION_SAVE_TO_LOCAL)) {
            //说明是想将数据存储到数据库中
            QRcodeInfo codeInfo = new QRcodeInfo();
            String head = result.substring(0, 4);
            fillQRcodeInfo(codeInfo, head);
            new QRcodeInfoManager(new DBHelper(this)).addQRcodeInfo(codeInfo);
        } else if (action.equals(ACTION_GET_FROM_LOCAL)) {
            //说明是想从数据库中获取数据
        }
    }

    /**
     * 封装成bean对象
     *
     * @param codeInfo
     * @param head
     */
    private void fillQRcodeInfo(QRcodeInfo codeInfo, String head) {
        if (head.equals("http")) {
            codeInfo.setQRcodeType(Constant.QRCODE_YTPE_URL);
            codeInfo.setQRcode(result);
            long time = System.currentTimeMillis();
            codeInfo.setScanTime(System.currentTimeMillis());
        } else {
            codeInfo.setQRcodeType(Constant.QRCODE_YTPE_NORMAL);
            codeInfo.setQRcode(result);
            codeInfo.setScanTime(System.currentTimeMillis());
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    class OperateDataThread extends Thread {

        @Override
        public void run() {
            handleQRcodeInfo(mAction);
        }
    }

}
