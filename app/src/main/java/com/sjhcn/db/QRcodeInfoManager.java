package com.sjhcn.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sjhcn.entitis.QRcodeInfo;

import java.util.List;

/**
 * Created by tong on 2016/7/13.
 */
public class QRcodeInfoManager implements QRcodeInfoIntf {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public QRcodeInfoManager(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public Boolean addQRcodeInfo(QRcodeInfo codeInfo) {
        try {
            if (db == null) {
                db = dbHelper.getWritableDatabase();
            }
            db.execSQL("insert into " + DBHelper.Tables.QRCODE_INFO + "(" +
                            DBHelper.QrInfoFileds.QR_CODE + "," +
                            DBHelper.QrInfoFileds.SCAN_TIME + "," +
                            DBHelper.QrInfoFileds.QR_CODE_TYPE + ") values (?,?,?)",
                    new Object[]{codeInfo.getQRcode(), codeInfo.getScanTime(), codeInfo.getQRcodeType()});
        } catch (Exception e) {
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    @Override
    public Boolean deleteQRcodeInfo(String QRcode) {
        try {
            if (db == null) {
                db = dbHelper.getReadableDatabase();
            }
            db.execSQL(" delete from " + DBHelper.Tables.QRCODE_INFO + " where " +
                    DBHelper.QrInfoFileds.QR_CODE + " =? ", new String[]{QRcode});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            db.close();
        }
        return false;
    }

    @Override
    public Boolean deleteQRcodeInfo(QRcodeInfo codeInfo) {
        try {
            if (db == null) {
                db = dbHelper.getReadableDatabase();
            }
            db.execSQL(" delete from " + DBHelper.Tables.QRCODE_INFO + " where " +
                    DBHelper.QrInfoFileds.QR_CODE + " =? ", new String[]{codeInfo.getQRcode()});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            db.close();
        }
        return false;
    }

    @Override
    public List<QRcodeInfo> queryQRcodeInfo() {
        List<QRcodeInfo> codeInfoList = null;
        Cursor cursor = null;
        try {
            if (db == null) {
                db = dbHelper.getReadableDatabase();
            }
            cursor = db.rawQuery("select * from " + DBHelper.Tables.QRCODE_INFO, null);
            while (cursor.moveToNext()) {
                QRcodeInfo codeInfo = new QRcodeInfo();
                codeInfo.setQRcode(cursor.getString(cursor.getColumnIndex(DBHelper.QrInfoFileds.QR_CODE)));
                codeInfo.setScanTime(cursor.getLong(cursor.getColumnIndex(DBHelper.QrInfoFileds.SCAN_TIME)));
                codeInfo.setQRcodeType(cursor.getInt(cursor.getColumnIndex(DBHelper.QrInfoFileds.QR_CODE_TYPE)));
                codeInfoList.add(codeInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();

        }
        return codeInfoList;
    }

    @Override
    public List<QRcodeInfo> queryQRcodeInfo(long scanTime) {
        List<QRcodeInfo> codeInfoList = null;
        Cursor cursor = null;
        try {
            if (db == null) {
                db = dbHelper.getReadableDatabase();
            }
            cursor = db.rawQuery("select * from " + DBHelper.Tables.QRCODE_INFO + " where " +
                    DBHelper.QrInfoFileds.SCAN_TIME + " =? ", new String[]{scanTime + ""});
            while (cursor.moveToNext()) {
                QRcodeInfo codeInfo = new QRcodeInfo();
                codeInfo.setQRcode(cursor.getString(cursor.getColumnIndex(DBHelper.QrInfoFileds.QR_CODE)));
                codeInfo.setScanTime(cursor.getLong(cursor.getColumnIndex(DBHelper.QrInfoFileds.SCAN_TIME)));
                codeInfo.setQRcodeType(cursor.getInt(cursor.getColumnIndex(DBHelper.QrInfoFileds.QR_CODE_TYPE)));
                codeInfoList.add(codeInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();

        }
        return codeInfoList;
    }
}
