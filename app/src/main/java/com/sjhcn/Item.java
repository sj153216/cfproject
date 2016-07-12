package com.sjhcn;

import android.graphics.Bitmap;

/**
 * Created by tong on 2016/7/12.
 */
public class Item {
    private Bitmap lable;

    public Bitmap getLable() {
        return lable;
    }

    public void setLable(Bitmap lable) {
        this.lable = lable;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Bitmap getArrow() {
        return arrow;
    }

    public void setArrow(Bitmap arrow) {
        this.arrow = arrow;
    }

    private String content;
    private Bitmap arrow;
}
