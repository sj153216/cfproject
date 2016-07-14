package com.sjhcn.application;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class QRcodeApplication extends Application implements
        Thread.UncaughtExceptionHandler {

    private static final String TAG = "EviApplication";
    private List<Activity> activityList = new LinkedList<Activity>();
    private Activity topActivity;
    private static QRcodeApplication instance;
    public static boolean isActive = true;
    public static boolean SHOW_NETWORK_TEXT = false;
    public static int netWorkType = 0;// -1:无网络访问，1:wifi,2移动网络,0:未能初始化
    /* 系统默认的Handler */
    private Thread.UncaughtExceptionHandler mDefaultHandler = null;

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
//             try {
//             Thread.sleep(3000);
//             } catch (Exception e) {
//             }
//             android.os.Process.killProcess(android.os.Process.myPid());
//             System.exit(1);
            // Intent intent = new Intent(this, LoginActivity.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
            // | Intent.FLAG_ACTIVITY_NEW_TASK);
            // startActivity(intent);
            // mDefaultHandler.uncaughtException(thread, ex);
            //  退出程序
            // android.os.Process.killProcess(android.os.Process.myPid());
            // System.exit(1);
        }
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        new Thread() {
            public void run() {
//                Looper.prepare();
//                ToastUtils.showLongToast(getApplicationContext(),
//                        Constants.HINT_INFO_APP_ERROR);
//                Looper.loop();
            }
        }.start();
        return true;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate()");
        instance = this;
        // httpClient = this.createHttpClient();

//        if (!Constants.DEBUG) {
//            /*
//             * 设置自己的异常退出handler
//             */
//            mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
//            // 设置该CrashHandler为程序的默认处理器
//            Thread.setDefaultUncaughtExceptionHandler(this);
    }



    // 单例模式中获取唯一的MyApplication实例
    public static QRcodeApplication getInstance() {
        if (null == instance) {
            instance = new QRcodeApplication();
        }
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void removeActivity(Activity act) {
        activityList.remove(act);
    }

    public void setTopActivity(Activity act) {
        topActivity = act;
    }

    public Activity getTopActivity() {
        return topActivity;
    }

    // 遍历所有Activity并finish
    public void exit() {
        try {
            for (Activity activity : activityList) {
                activity.finish();
            }
            // 清理相关数据
            //DataManager.getInstance().clean();
            wait(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // DataManagerService.getInstance().release();
            System.exit(0);
        }
    }

    // private HttpClient httpClient;

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        // this.shutdownHttpClient();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // this.shutdownHttpClient();
    }



}
