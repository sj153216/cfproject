package com.sjhcn.db;

import com.sjhcn.entitis.QRcodeInfo;

import java.util.List;

/**
 * Created by tong on 2016/7/13.
 */
public interface QRcodeInfoIntf {
    /**
     * 向数据库插入二维码信息
     *
     * @param codeInfo
     * @return
     */
    public Boolean addQRcodeInfo(QRcodeInfo codeInfo);

    /**
     * 根据二维码字符串删除数据库中的二维码信息
     *
     * @param QRcode
     * @return
     */
    public Boolean deleteQRcodeInfo(String QRcode);

    /**
     * 根据二维码信息删除数据库中的二维码信息
     *
     * @param codeInfo
     * @return
     */
    public Boolean deleteQRcodeInfo(QRcodeInfo codeInfo);

    /**
     * 查询全部的二维码信息
     *
     * @return
     */
    public List<QRcodeInfo> queryQRcodeInfo();

    /**
     * 根据扫描的时间查询二维码信息
     *
     * @return
     */
    public List<QRcodeInfo> queryQRcodeInfo(long scanTime);


}
