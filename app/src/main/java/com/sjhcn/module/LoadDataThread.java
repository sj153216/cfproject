package com.sjhcn.module;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.sjhcn.constants.Constant;
import com.sjhcn.db.QRcodeMakeInfoIntf;
import com.sjhcn.db.QRcodeScanInfoIntf;

/**
 * Created by tong on 2016/7/14.
 */
public class LoadDataThread extends Thread {

    //主线程的handler
    private Handler mMainHandler;
    //子线程的handler
    private Handler mWorkHandler;

    private Looper mLooper;
    private QRcodeScanInfoIntf scanCodeInfoMgr;
    private QRcodeMakeInfoIntf makeCodeInfoMgr;

    public LoadDataThread(Handler mainHandler, QRcodeScanInfoIntf scanCodeInfoMgr, QRcodeMakeInfoIntf makeCodeInfoMgr) {
        this.mMainHandler = mainHandler;
        this.scanCodeInfoMgr = scanCodeInfoMgr;
        this.makeCodeInfoMgr = makeCodeInfoMgr;
    }

    @Override
    public void run() {
        Looper.prepare();
        mLooper = Looper.myLooper();
        mWorkHandler = new Handler(mLooper) {
            @Override
            public void handleMessage(Message msg) {
                int action = msg.what;
                queryQRcodeInfo(action);
            }
        };
        Looper.loop();

    }

    /**
     * 根据action从数据库获取信息
     *
     * @param action
     */
    private void queryQRcodeInfo(int action) {
        Object queryResult = null;
        try {
            switch (action) {
                case Constant.ACTION_LOAD_SCAN_QRCODEINFO:
                    //加载扫描二维码数据
                    queryResult = scanCodeInfoMgr.queryQRcodeScanInfo();
                    break;
                case Constant.ACTION_LOAD_MAKE_QRCODEINFO:
                    //加载制码二维码数据
                    queryResult = makeCodeInfoMgr.queryQRcodeMakeInfo();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendMessage(action, queryResult);

    }

    /**
     * 将查询的结果发送到主线程
     *
     * @param action
     * @param queryResult
     */
    private void sendMessage(int action, Object queryResult) {
        Message msg = mMainHandler.obtainMessage();
        msg.what = action;
        msg.obj = queryResult;
        msg.sendToTarget();
    }

    /**
     * datamanager调用，告诉loop线程有任务来了
     *
     * @param action
     */
    public void addTask(int action) {
        if (mWorkHandler != null) {
            mWorkHandler.sendEmptyMessage(action);
        }
    }


}
