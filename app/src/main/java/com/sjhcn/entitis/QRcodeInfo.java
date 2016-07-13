package com.sjhcn.entitis;

/**
 * Created by tong on 2016/7/13.
 */
public class QRcodeInfo {
    private String QRcode;
    private long scanTime;
    private int QRcodeType;

    public int getQRcodeType() {
        return QRcodeType;
    }

    public void setQRcodeType(int QRcodeType) {
        this.QRcodeType = QRcodeType;
    }

    public String getQRcode() {
        return QRcode;
    }

    public void setQRcode(String QRcode) {
        this.QRcode = QRcode;
    }

    public long getScanTime() {
        return scanTime;
    }

    public void setScanTime(long scanTime) {
        this.scanTime = scanTime;
    }
}
