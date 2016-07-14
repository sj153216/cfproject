package com.sjhcn.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.sjhcn.qrcode.R;

/**
 * Created by tong on 2016/7/14.
 */
public class MyProgressDialog extends ProgressDialog {

    public MyProgressDialog(Context context) {
        super(context);
    }

    public MyProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
    }



//    public static MyProgressDialog show(Context ctx) {
//        MyProgressDialog dialog = new MyProgressDialog(ctx);
//        dialog.show();
//        return dialog;
//    }


}
