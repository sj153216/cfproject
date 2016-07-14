package com.sjhcn.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.CRC32;

public class Utils {
    private static final String TAG = "Utils";
    public static final String NETWORK_MOBILE_2G = "2G手机数据网络";
    public static final String NETWORK_MOBILE_3G = "3G手机数据网络";
    public static final String NETWORK_WIFI = "WiFi网络";

    /**
     * 获得当前系统时间
     *
     * @return
     */
    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        String time = c.get(Calendar.YEAR)
                + "-"
                + formatDataTime(c.get(Calendar.MONTH) + 1)
                + "-"// month加一
                + formatDataTime(c.get(Calendar.DAY_OF_MONTH)) + " "
                + formatDataTime(c.get(Calendar.HOUR_OF_DAY)) + ":"
                + formatDataTime(c.get(Calendar.MINUTE)) + ":"
                + formatDataTime(c.get(Calendar.SECOND));
        return time;
    }

    /**
     * 日期格式化
     *
     * @param t
     * @return
     */
    public static String formatDataTime(int t) {
        return t >= 10 ? "" + t : "0" + t;// 三元运算符 t>10时取 ""+t
    }

    /**
     * 将时间类型转换为pattern 格式的字符串
     *
     * @param pattern 字符串格式
     * @param date    时间
     * @return 字符串格式的时间
     */
    public static String dateToStr(String pattern, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 获取网络类型的文字信息
     *
     * @param ctx
     * @return
     */
    public static String getNetWorkTypeInfo(Context ctx) {
        String ret_val = "";
        if (ctx != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                int network_type = mNetworkInfo.getType();
                if (network_type == ConnectivityManager.TYPE_MOBILE) {
                    int net_type = mNetworkInfo.getSubtype();
                    if (net_type == TelephonyManager.NETWORK_TYPE_GPRS
                            || net_type == TelephonyManager.NETWORK_TYPE_CDMA
                            || net_type == TelephonyManager.NETWORK_TYPE_EDGE) {
                        ret_val = NETWORK_MOBILE_2G;
                    } else {
                        ret_val = NETWORK_MOBILE_3G;
                    }
                } else if (network_type == ConnectivityManager.TYPE_WIFI) {
                    ret_val = NETWORK_WIFI;
                } else {
                    ret_val = "";
                }
            }
        }
        return ret_val;
    }

    /**
     * 获取SD卡的根目录
     *
     * @return
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }
        if (sdDir == null) {
            return "";
        } else {
            return sdDir.toString();
        }
    }

    /**
     * 获取IMEI号
     *
     * @param ctx
     * @return
     */
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            String imei = tm.getDeviceId() != null ? tm.getDeviceId() : "";
            Log.d("Utils", "IMEI:" + imei);
            if (imei.equals("0")) {
                Log.d(TAG, "FAKE IMEI as 098765432112345");
                imei = "987654321012345";
            }
            int len = 15 - imei.length();
            for (int i = 0; i < len; i++) {
                imei += "0";
            }
            return "863984026009796";
        }
        return "";
    }

    /**
     * 当前网络是否连接
     *
     * @param context ：上下文
     * @return：true，false
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static long getCRC(byte[] buf) {
        CRC32 crc = new CRC32();
        crc.update(buf);
        return crc.getValue();
    }

    public static boolean checkCRC(long value, byte[] bytes) {
        CRC32 crc = new CRC32();
        crc.update(bytes);
        long checkV = crc.getValue();
        if (value == checkV) {
            return true;
        }
        return false;
    }

    /**
     * 从文件路径中获取文件名
     *
     * @param path ： 文件路径
     * @return：文件名
     */
    public static String getFileNameFromPath(String path) {
        String ret_val = "";
        if (path != null && TextUtils.isEmpty(path) == false) {
            ret_val = path.substring(path.lastIndexOf("/") + 1);
        }
        return ret_val;
    }

    /**
     * 从文件中获取Bitmap
     *
     * @param path
     * @return
     */
    public static Bitmap getBitmapFromFile(String path) {
        Bitmap bm = null;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = Utils.computeSampleSize(opts, -1, 400 * 400);
        opts.inJustDecodeBounds = false;
        try {
            // bm = ThumbnailUtils.extractThumbnail(tBitmap, imageRef.width,
            // imageRef.height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
            bm = BitmapFactory.decodeFile(path, opts);
        } catch (OutOfMemoryError err) {
        }
        return bm;
    }

    /* 动态计算sample size */
    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    /* 动态计算sample size */
    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 删除目录
     *
     * @param path ：目录路径
     * @return：true：成功，false，失败。
     */
    public static boolean deleteDir(String path) {
        boolean success = true;
        File file = new File(path);
        if (file.exists()) {
            File[] list = file.listFiles();
            if (list != null) {
                int len = list.length;
                for (int i = 0; i < len; ++i) {
                    if (list[i].isDirectory()) {
                        deleteDir(list[i].getPath());
                    } else {
                        boolean ret = list[i].delete();
                        if (!ret) {
                            success = false;
                        }
                    }
                }
            }
        } else {
            success = false;
        }

        if (success) {
            file.delete();
        }
        return success;
    }

    /**
     * 返回当前版本号的值，返回类型为整形
     *
     * @param context
     * @return
     */
    public static int getVersionValue(Context context) {
        int ret_val = 0;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            ret_val = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret_val;
    }

    /**
     * 得到手机所有内存卡路径
     *
     * @return List<String> 路径集合
     * @throws IOException
     */
    public static File[] getExtSDCardPaths() {
        List<String> paths = new ArrayList<String>();
        String extFileStatus = Environment.getExternalStorageState();
        File extFile = Environment.getExternalStorageDirectory();

        if (extFileStatus.equals(Environment.MEDIA_MOUNTED) && extFile.exists()
                && extFile.isDirectory() && extFile.canWrite()) {// 如果sd是挂载状态且sd根目录是存在并且是个目录而还是可写的
            paths.add(extFile.getAbsolutePath());// 取得路径并且加入到路径集合
        }
        try {
            // obtain executed result of command line code of 'mount', to judge
            // whether tfCard exists by the result
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("mount");
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            int mountPathIndex = 1;
            while ((line = br.readLine()) != null) {
                // format of sdcard file system: vfat/fuse
                if ((!line.contains("fat") && !line.contains("fuse") && !line
                        .contains("storage"))
                        || line.contains("secure")
                        || line.contains("asec")
                        || line.contains("firmware")
                        || line.contains("shell")
                        || line.contains("obb")
                        || line.contains("legacy") || line.contains("data")) {
                    continue;
                }
                String[] parts = line.split(" ");
                int length = parts.length;
                if (mountPathIndex >= length) {
                    continue;
                }
                String mountPath = parts[mountPathIndex];
                if (!mountPath.contains("/") || mountPath.contains("data")
                        || mountPath.contains("Data")) {
                    continue;
                }
                File mountRoot = new File(mountPath);
                if (!mountRoot.exists() || !mountRoot.isDirectory()
                        || !mountRoot.canWrite()) {
                    continue;
                }
                boolean equalsToPrimarySD = mountPath.equals(extFile
                        .getAbsolutePath());
                if (equalsToPrimarySD) {
                    continue;
                }
                paths.add(mountPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        File[] f;
        if (paths.size() == 1) {
            f = new File[1];
            f[0] = new File(paths.get(0));
        } else {
            f = new File[2];
            f[0] = new File(paths.get(0));
            f[1] = new File(paths.get(1));
        }

        return f;
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名，要在系统内保持唯一
     * @return boolean 删除成功的标志
     */
    public static boolean deleteFile(String fileName) {
        File f = new File(fileName);
        return f.delete();
    }

    /**
     * 文件是否存在
     *
     * @param context
     * @param fileName
     * @return
     */
    public static boolean exists(Context context, String fileName) {
        return new File(context.getFilesDir(), fileName).exists();
    }

    /**
     * 读取文本数据
     *
     * @param filePath 文件名
     * @return String, 读取到的文本内容，失败返回null
     */
    public static String readFile(String filePath) {
        if (filePath == null || !new File(filePath).exists()) {
            return null;
        }
        FileInputStream fis = null;
        String content = null;
        try {
            fis = new FileInputStream(filePath);
            if (fis != null) {

                byte[] buffer = new byte[1024];
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                while (true) {
                    int readLength = fis.read(buffer);
                    if (readLength == -1)
                        break;
                    arrayOutputStream.write(buffer, 0, readLength);
                }
                fis.close();
                arrayOutputStream.close();
                content = new String(arrayOutputStream.toByteArray());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            content = null;
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return content;
    }

    /**
     * 读取文本数据
     *
     * @param context  程序上下文
     * @param fileName 文件名
     * @return String, 读取到的文本内容，失败返回null
     */
    public static String readAssets(Context context, String fileName) {
        InputStream is = null;
        String content = null;
        try {
            is = context.getAssets().open(fileName);
            if (is != null) {

                byte[] buffer = new byte[1024];
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                while (true) {
                    int readLength = is.read(buffer);
                    if (readLength == -1)
                        break;
                    arrayOutputStream.write(buffer, 0, readLength);
                }
                is.close();
                arrayOutputStream.close();
                content = new String(arrayOutputStream.toByteArray());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            content = null;
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return content;
    }

    /**
     * 根据日期计算返回的日期
     */
    public static int caculateDate(int year, int month, int temp) {
        if (month == 5 || month == 7 || month == 8 || month == 10
                || month == 12) {
            return temp + 30;
        } else if (month == 1 || month == 2 || month == 4 || month == 6
                || month == 9 || month == 11) {
            return temp + 31;
        } else {
            if (year % 4 != 0) {
                return temp + 28;
            } else {
                if ((year % 100) == 0 && (year % 400 != 0)) {
                    return temp + 28;
                } else {
                    return temp + 29;
                }
            }
        }
    }

    /**
     * 判断字符创是不是以http开头
     *
     * @param s
     * @return
     */
    public static Boolean headWithHttp(String s) {
        String head = s.substring(0, 4);
        if (head.equals("http")) {
            return true;
        } else {
            return false;
        }
    }

}
