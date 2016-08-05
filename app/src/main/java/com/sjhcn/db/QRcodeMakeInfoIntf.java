package com.sjhcn.db;

import com.sjhcn.entitis.QRcodeMakeInfo;

import java.util.List;

/**
 * Created by tong on 2016/7/13.
 */
public interface QRcodeMakeInfoIntf {
    /**
     * 向数据库插入二维码信息
     *
     * @param codeInfo
     * @return
     */
    public Boolean addQRcodeMakeInfo(QRcodeMakeInfo codeInfo);

    /**
     * 根据二维码字符串删除数据库中的二维码信息
     *
     * @param QRcode
     * @return
     */
    public Boolean deleteQRcodeMakeInfo(String QRcode);

    /**
     * 根据二维码信息删除数据库中的二维码信息
     *
     * @param codeInfo
     * @return
     */
    public Boolean deleteQRcodeMakeInfo(QRcodeMakeInfo codeInfo);

    /**
     * 查询全部的二维码信息
     *
     * @return
     */
    public List<QRcodeMakeInfo> queryQRcodeMakeInfo();

    /**
     * 根据扫描的时间查询二维码信息
     *
     * @return
     */
    public List<QRcodeMakeInfo> queryQRcodeMakeInfo(long scanTime);


}
