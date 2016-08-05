package com.sjhcn.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sjhcn on 2016/7/15.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "qrcode_data.db";
    private static final int DATABASE_VERSION = 2;
    private Context mContext;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    public static final String CREATE_TABLE_QRCODE_SCAN_INFO_SQL_STATEMENT = "CREATE TABLE "
            + Tables.QRCODE_SCAN_INFO
            + "( "
            + QrInfoScanFileds.ID
            + " INTEGER PRIMARY KEY, "
            + QrInfoScanFileds.QR_CODE
            + " TEXT, "
            + QrInfoScanFileds.SCAN_TIME
            + " LONG, "
            + QrInfoScanFileds.QR_CODE_TYPE
            + "  INTEGER );";
    public static final String CREATE_TABLE_QRCODE_MAKE_INFO_SQL_STATEMENT = "CREATE TABLE "
            + Tables.QRCODE_MAKE_INFO
            + "( "
            + QrInfoMakeFileds.ID
            + " INTEGER PRIMARY KEY, "
            + QrInfoMakeFileds.QR_CODE
            + " TEXT, "
            + QrInfoMakeFileds.MAKE_TIME
            + " LONG, "
            + QrInfoMakeFileds.QR_CODE_TYPE
            + "  INTEGER );";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QRCODE_SCAN_INFO_SQL_STATEMENT);
        db.execSQL(CREATE_TABLE_QRCODE_MAKE_INFO_SQL_STATEMENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (DATABASE_VERSION > oldVersion) {
            db.execSQL("DROP TABLE qrcode_info_table ");
            db.execSQL(CREATE_TABLE_QRCODE_SCAN_INFO_SQL_STATEMENT);
        }
    }

    public interface Tables {
        public static final String QRCODE_SCAN_INFO = "qrcode_scan_info_table";
        public static final String QRCODE_MAKE_INFO = "qrcode_make_info_table";
    }

    public interface QrInfoScanFileds {
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

    public interface QrInfoMakeFileds {
        /**
         * 主键ID
         **/
        public static final String ID = "_id";
        /**
         * 对应的二维码
         **/
        public static final String QR_CODE = "qr_code";

        /**
         * 制码的时间
         */
        public static final String MAKE_TIME = "make_time";
        /**
         * 二维码的类型
         */
        public static final String QR_CODE_TYPE = "qr_code_type";

    }


}
