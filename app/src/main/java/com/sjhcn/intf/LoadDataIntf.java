package com.sjhcn.intf;

/**
 * 数据回调接口
 * Created by sjhcn on 2016/7/17.
 */
public interface LoadDataIntf {

    /**
     * 本地加载数据完成
     */
    public void onLoadFinish(int action);

    /**
     * 开始加载本地数据
     */
    public void onLoadStart();
}
