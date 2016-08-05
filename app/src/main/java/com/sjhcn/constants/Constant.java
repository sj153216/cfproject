package com.sjhcn.constants;

/**
 * Created by tong on 2016/7/13.
 */
public class Constant {

    //扫描得到的二维码类型
    public static final int SCAN_QRCODE_BASE = 0;
    public static final int SCAN_QRCODE_YTPE_URL = SCAN_QRCODE_BASE + 1;
    public static final int SCAN_QRCODE_YTPE_NORMAL = SCAN_QRCODE_BASE + 2;


    //制码得到的二维码类型
    public static final int MAKE_QRCODE_BASE = 10;
    public static final int MAKE_QRCODE_YTPE_URL = MAKE_QRCODE_BASE + 1;
    public static final int MAKE_QRCODE_YTPE_NORMAL = MAKE_QRCODE_BASE + 2;
    public static final int MAKE_QRCODE_YTPE_MAP = MAKE_QRCODE_BASE + 3;

    //从数据库加载扫描的二维码信息
    public static final int ACTION_LOAD_SCAN_QRCODEINFO = 20;
    //从数据库加载制码的二维码信息
    public static final int ACTION_LOAD_MAKE_QRCODEINFO = 21;


    //制码界面，不同种类的制码action
    public static final int ACTION_GENERATE_NAME_QRCODEINFO = 22;
    public static final int ACTION_GENERATE_URL_QRCODEINFO = 23;
    public static final int ACTION_GENERATE_MAP_QRCODEINFO = 24;


}
