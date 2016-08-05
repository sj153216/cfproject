package com.sjhcn.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sjhcn.application.QRcodeApplication;
import com.sjhcn.entitis.QRcodeMakeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong on 2016/7/13.
 */
public class QRcodeMakeInfoManager implements QRcodeMakeInfoIntf {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private static QRcodeMakeInfoManager mInstance;

    private QRcodeMakeInfoManager(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public static QRcodeMakeInfoManager getInstance() {
        if (mInstance == null) {
            synchronized ("") {
                if (mInstance == null) {
                    mInstance = new QRcodeMakeInfoManager(new DBHelper(QRcodeApplication.getInstance()));
                }
            }
        }
        return mInstance;
    }

    @Override
    public synchronized Boolean addQRcodeMakeInfo(QRcodeMakeInfo codeInfo) {
        try {
            if (db == null) {
                db = dbHelper.getWritableDatabase();
            }
            db.execSQL("insert into " + DBHelper.Tables.QRCODE_MAKE_INFO + "(" +
                            DBHelper.QrInfoMakeFileds.QR_CODE + "," +
                            DBHelper.QrInfoMakeFileds.MAKE_TIME + "," +
                            DBHelper.QrInfoMakeFileds.QR_CODE_TYPE + ") values (?,?,?)",
                    new Object[]{codeInfo.getQRcode(), codeInfo.getMakeTime(), codeInfo.getQRcodeType()});
        } catch (Exception e) {
            return false;
        } finally {

        }
        return true;
    }

    @Override
    public synchronized Boolean deleteQRcodeMakeInfo(String QRcode) {
        try {
            if (db == null) {
                db = dbHelper.getReadableDatabase();
            }
            db.execSQL(" delete from " + DBHelper.Tables.QRCODE_MAKE_INFO + " where " +
                    DBHelper.QrInfoMakeFileds.QR_CODE + " =? ", new String[]{QRcode});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return false;
    }

    @Override
    public synchronized Boolean deleteQRcodeMakeInfo(QRcodeMakeInfo codeInfo) {
        try {
            if (db == null) {
                db = dbHelper.getReadableDatabase();
            }
            db.execSQL(" delete from " + DBHelper.Tables.QRCODE_MAKE_INFO + " where " +
                    DBHelper.QrInfoMakeFileds.QR_CODE + " =? ", new String[]{codeInfo.getQRcode()});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return false;
    }

    @Override
    public synchronized List<QRcodeMakeInfo> queryQRcodeMakeInfo() {
        List<QRcodeMakeInfo> codeInfoList = new ArrayList<QRcodeMakeInfo>();
        Cursor cursor = null;
        try {
            if (db == null) {
                db = dbHelper.getReadableDatabase();
            }
            cursor = db.rawQuery("select * from " + DBHelper.Tables.QRCODE_MAKE_INFO, null);
            while (cursor.moveToNext()) {
                QRcodeMakeInfo codeInfo = new QRcodeMakeInfo();
                codeInfo.setQRcode(cursor.getString(cursor.getColumnIndex(DBHelper.QrInfoMakeFileds.QR_CODE)));
                codeInfo.setMakeTime(cursor.getLong(cursor.getColumnIndex(DBHelper.QrInfoMakeFileds.MAKE_TIME)));
                codeInfo.setQRcodeType(cursor.getInt(cursor.getColumnIndex(DBHelper.QrInfoMakeFileds.QR_CODE_TYPE)));
                codeInfoList.add(codeInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }

        }
        return codeInfoList;
    }

    @Override
    public synchronized List<QRcodeMakeInfo> queryQRcodeMakeInfo(long makeTime) {
        List<QRcodeMakeInfo> codeInfoList = null;
        Cursor cursor = null;
        try {
            if (db == null) {
                db = dbHelper.getReadableDatabase();
            }
            cursor = db.rawQuery("select * from " + DBHelper.Tables.QRCODE_MAKE_INFO + " where " +
                    DBHelper.QrInfoMakeFileds.MAKE_TIME + " =? ", new String[]{makeTime + ""});
            while (cursor.moveToNext()) {
                QRcodeMakeInfo codeInfo = new QRcodeMakeInfo();
                codeInfo.setQRcode(cursor.getString(cursor.getColumnIndex(DBHelper.QrInfoMakeFileds.QR_CODE)));
                codeInfo.setMakeTime(cursor.getLong(cursor.getColumnIndex(DBHelper.QrInfoMakeFileds.MAKE_TIME)));
                codeInfo.setQRcodeType(cursor.getInt(cursor.getColumnIndex(DBHelper.QrInfoMakeFileds.QR_CODE_TYPE)));
                codeInfoList.add(codeInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        return codeInfoList;
    }
}
