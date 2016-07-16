package com.sjhcn.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sjhcn on 2016/7/15.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "qrcode_data.db";
    private static final int DATABASE_VERSION = 1;
    private Context mContext;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    public static final String CREATE_TABLE_QRCODE_INFO_SQL_STATEMENT = "CREATE TABLE "
            + Tables.QRCODE_INFO
            + "( "
            + QrInfoFileds.ID
            + " INTEGER PRIMARY KEY, "
            + QrInfoFileds.QR_CODE
            + " TEXT, "
            + QrInfoFileds.SCAN_TIME
            + " LONG, "
            + QrInfoFileds.QR_CODE_TYPE
            + "  INTEGER );";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QRCODE_INFO_SQL_STATEMENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public interface Tables {
        public static final String QRCODE_INFO = "qrcode_info_table";
    }

    public interface QrInfoFileds {
        /**
         * 主键ID
         **/
        public static final String ID = "_id";
        /**
         * 对应的二维码
         **/
        public static final String QR_CODE = "qr_code";

        /**
         * 扫码的时间
         */
        public static final String SCAN_TIME = "scan_time";
        /**
         * 二维码的类型
         */
        public static final String QR_CODE_TYPE = "qr_code_type";

    }


}
