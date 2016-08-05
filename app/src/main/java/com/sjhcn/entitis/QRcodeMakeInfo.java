package com.sjhcn.entitis;

/**
 * 制码
 * Created by sjhcn on 2016/8/2.
 */
public class QRcodeMakeInfo {
    private String QRcode;
    private long makeTime;
    private int QRcodeType;

    public String getQRcode() {
        return QRcode;
    }

    public void setQRcode(String QRcode) {
        this.QRcode = QRcode;
    }

    public long getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(long makeTime) {
        this.makeTime = makeTime;
    }

    public int getQRcodeType() {
        return QRcodeType;
    }

    public void setQRcodeType(int QRcodeType) {
        this.QRcodeType = QRcodeType;
    }
}
