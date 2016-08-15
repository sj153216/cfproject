package com.sjhcn.entitis;

import cn.bmob.v3.BmobObject;

/**
 * 用户提交建议的对应类
 * Created by sjhcn on 2016/8/15.
 */
public class SuggestionInfo extends BmobObject {

    private String userName;
    private String suggestion;
    private String email;

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
