package com.sjhcn.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.sjhcn.constants.Constant;
import com.sjhcn.db.QRcodeMakeInfoManager;
import com.sjhcn.db.QRcodeScanInfoManager;
import com.sjhcn.entitis.QRcodeMakeInfo;
import com.sjhcn.entitis.QRcodeScanInfo;

/**
 * Created by sjhcn on 2016/7/16.
 */
public class HandleQRcodeService extends Service {

    //将扫描得到的二维码存入数数据库
    public static final String ACTION_SAVE_SCAN_TO_LOCAL = "save_scan_to_local";
    //将制码得到的二维码存入数数据库
    public static final String ACTION_SAVE_MAKE_TO_LOCAL = "save_make_to_local";
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
        if (action.equals(ACTION_SAVE_SCAN_TO_LOCAL)) {
            //将扫描得到的二维码存入数据库
            QRcodeScanInfo codeInfo = new QRcodeScanInfo();
            String head = result.substring(0, 4);
            fillQRcodeScanInfo(codeInfo, head);
            QRcodeScanInfoManager.getInstance().addQRcodeScanInfo(codeInfo);
        } else if (action.equals(ACTION_SAVE_MAKE_TO_LOCAL)) {
            //将制码得到的二维码存入数据库
            QRcodeMakeInfo codeInfo = new QRcodeMakeInfo();
            String head = result.substring(0, 4);
            fillQRcodeMakeInfo(codeInfo, head);
            QRcodeMakeInfoManager.getInstance().addQRcodeMakeInfo(codeInfo);

        }
    }

    /**
     * 封装成bean对象
     *
     * @param codeInfo
     * @param head
     */
    private void fillQRcodeMakeInfo(QRcodeMakeInfo codeInfo, String head) {
        if (head.equals("http")) {
            codeInfo.setQRcodeType(Constant.MAKE_QRCODE_YTPE_URL);
            codeInfo.setQRcode(result);
            codeInfo.setMakeTime(System.currentTimeMillis());
        } else if (head.substring(0, 2).equals("坐标")) {
            codeInfo.setQRcodeType(Constant.MAKE_QRCODE_YTPE_MAP);
            codeInfo.setQRcode(result);
            codeInfo.setMakeTime(System.currentTimeMillis());
        } else {
            codeInfo.setQRcodeType(Constant.MAKE_QRCODE_YTPE_NORMAL);
            codeInfo.setQRcode(result);
            codeInfo.setMakeTime(System.currentTimeMillis());
        }
    }

    /**
     * 封装成bean对象
     *
     * @param codeInfo
     * @param head
     */
    private void fillQRcodeScanInfo(QRcodeScanInfo codeInfo, String head) {
        if (head.equals("http")) {
            codeInfo.setQRcodeType(Constant.SCAN_QRCODE_YTPE_URL);
            codeInfo.setQRcode(result);
            codeInfo.setScanTime(System.currentTimeMillis());
        } else {
            codeInfo.setQRcodeType(Constant.SCAN_QRCODE_YTPE_NORMAL);
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
