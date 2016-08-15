package com.sjhcn.view;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by sjhcn on 2016/8/15.
 */
public class MyDialog extends Dialog {
    public MyDialog(Context context) {
        super(context);

    }

    protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

    }

    public MyDialog(Context context, int theme) {
        super(context, theme);

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        setContentView(R.layout.dialog_show_position);
//
//    }
}
