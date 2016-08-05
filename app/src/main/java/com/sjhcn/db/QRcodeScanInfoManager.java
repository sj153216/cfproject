package com.sjhcn.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sjhcn.application.QRcodeApplication;
import com.sjhcn.entitis.QRcodeScanInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tong on 2016/7/13.
 */
public class QRcodeScanInfoManager implements QRcodeScanInfoIntf {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private static QRcodeScanInfoManager mInstance;

    private QRcodeScanInfoManager(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public static QRcodeScanInfoManager getInstance() {
        if (mInstance == null) {
            synchronized ("") {
                if (mInstance == null) {
                    mInstance = new QRcodeScanInfoManager(new DBHelper(QRcodeApplication.getInstance()));
                }
            }
        }
        return mInstance;
    }

    @Override
    public synchronized Boolean addQRcodeScanInfo(QRcodeScanInfo codeInfo) {
        try {
            if (db == null) {
                db = dbHelper.getWritableDatabase();
            }
            db.execSQL("insert into " + DBHelper.Tables.QRCODE_SCAN_INFO + "(" +
                            DBHelper.QrInfoScanFileds.QR_CODE + "," +
                            DBHelper.QrInfoScanFileds.SCAN_TIME + "," +
                            DBHelper.QrInfoScanFileds.QR_CODE_TYPE + ") values (?,?,?)",
                    new Object[]{codeInfo.getQRcode(), codeInfo.getScanTime(), codeInfo.getQRcodeType()});
        } catch (Exception e) {
            return false;
        } finally {

        }
        return true;
    }

    @Override
    public synchronized Boolean deleteQRcodeScanInfo(String QRcode) {
        try {
            if (db == null) {
                db = dbHelper.getReadableDatabase();
            }
            db.execSQL(" delete from " + DBHelper.Tables.QRCODE_SCAN_INFO + " where " +
                    DBHelper.QrInfoScanFileds.QR_CODE + " =? ", new String[]{QRcode});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return false;
    }

    @Override
    public synchronized Boolean deleteQRcodeScanInfo(QRcodeScanInfo codeInfo) {
        try {
            if (db == null) {
                db = dbHelper.getReadableDatabase();
            }
            db.execSQL(" delete from " + DBHelper.Tables.QRCODE_SCAN_INFO + " where " +
                    DBHelper.QrInfoScanFileds.QR_CODE + " =? ", new String[]{codeInfo.getQRcode()});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return false;
    }

    @Override
    public synchronized List<QRcodeScanInfo> queryQRcodeScanInfo() {
        List<QRcodeScanInfo> codeInfoList = new ArrayList<QRcodeScanInfo>();
        Cursor cursor = null;
        try {
            if (db == null) {
                db = dbHelper.getReadableDatabase();
            }
            cursor = db.rawQuery("select * from " + DBHelper.Tables.QRCODE_SCAN_INFO, null);
            while (cursor.moveToNext()) {
                QRcodeScanInfo codeInfo = new QRcodeScanInfo();
                codeInfo.setQRcode(cursor.getString(cursor.getColumnIndex(DBHelper.QrInfoScanFileds.QR_CODE)));
                codeInfo.setScanTime(cursor.getLong(cursor.getColumnIndex(DBHelper.QrInfoScanFileds.SCAN_TIME)));
                codeInfo.setQRcodeType(cursor.getInt(cursor.getColumnIndex(DBHelper.QrInfoScanFileds.QR_CODE_TYPE)));
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
    public synchronized List<QRcodeScanInfo> queryQRcodeScanInfo(long scanTime) {
        List<QRcodeScanInfo> codeInfoList = null;
        Cursor cursor = null;
        try {
            if (db == null) {
                db = dbHelper.getReadableDatabase();
            }
            cursor = db.rawQuery("select * from " + DBHelper.Tables.QRCODE_SCAN_INFO + " where " +
                    DBHelper.QrInfoScanFileds.SCAN_TIME + " =? ", new String[]{scanTime + ""});
            while (cursor.moveToNext()) {
                QRcodeScanInfo codeInfo = new QRcodeScanInfo();
                codeInfo.setQRcode(cursor.getString(cursor.getColumnIndex(DBHelper.QrInfoScanFileds.QR_CODE)));
                codeInfo.setScanTime(cursor.getLong(cursor.getColumnIndex(DBHelper.QrInfoScanFileds.SCAN_TIME)));
                codeInfo.setQRcodeType(cursor.getInt(cursor.getColumnIndex(DBHelper.QrInfoScanFileds.QR_CODE_TYPE)));
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
