package com.sjhcn.module;

/**
 * 数据管理类
 * Created by sjhcn on 2016/7/16.
 */
public class DataManager {


    private static  DataManager mInstance = null;

    private DataManager() {

    }

    /**
     * DCL单例模式
     * @return
     */
    public static DataManager getInstance() {
        if(mInstance == null){
            synchronized (""){
                if(mInstance == null){
                    mInstance = new DataManager();
                }
            }
        }
        return mInstance;
    }
}
