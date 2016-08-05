package com.sjhcn.db;

import com.sjhcn.entitis.QRcodeScanInfo;

import java.util.List;

/**
 * Created by tong on 2016/7/13.
 */
public interface QRcodeScanInfoIntf {
    /**
     * 向数据库插入二维码信息
     *
     * @param codeInfo
     * @return
     */
    public Boolean addQRcodeScanInfo(QRcodeScanInfo codeInfo);

    /**
     * 根据二维码字符串删除数据库中的二维码信息
     *
     * @param QRcode
     * @return
     */
    public Boolean deleteQRcodeScanInfo(String QRcode);

    /**
     * 根据二维码信息删除数据库中的二维码信息
     *
     * @param codeInfo
     * @return
     */
    public Boolean deleteQRcodeScanInfo(QRcodeScanInfo codeInfo);

    /**
     * 查询全部的二维码信息
     *
     * @return
     */
    public List<QRcodeScanInfo> queryQRcodeScanInfo();

    /**
     * 根据扫描的时间查询二维码信息
     *
     * @return
     */
    public List<QRcodeScanInfo> queryQRcodeScanInfo(long scanTime);


}
