package com.sjhcn.entitis;

import android.graphics.Bitmap;

/**
 * Created by tong on 2016/7/14.
 */
public class ScanRecordItem {
    private Bitmap lable;
    private Bitmap arrow;
    private String qrcode;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Bitmap getLable() {
        return lable;
    }

    public void setLable(Bitmap lable) {
        this.lable = lable;
    }

    public Bitmap getArrow() {
        return arrow;
    }

    public void setArrow(Bitmap arrow) {
        this.arrow = arrow;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    private String time;

}
