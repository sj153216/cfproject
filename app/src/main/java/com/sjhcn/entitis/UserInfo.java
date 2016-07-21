package com.sjhcn.entitis;

import cn.bmob.v3.BmobUser;

/**
 * Created by sjhcn on 2016/8/8.
 */
public class UserInfo extends BmobUser {
    private String userName;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String password;



}
