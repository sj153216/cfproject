package com.example.imageloader.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imageloader.R;
import com.example.imageloader.adapter.ImageAdapter;
import com.example.imageloader.bean.FolderBean;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends Activity implements ImageDirPopupWindow.OnItemClickListener {


    private GridView mGridView;
    private List<String> mImgs;
    private String[] mPictures;
    private ImageAdapter mImgAdapter;

    private RelativeLayout mBottomLy;
    private TextView mDirName;
    private TextView mDirCount;

    private File mCurrentDir;
    private int mMaxCount;

    private List<FolderBean> mFolderBeans = new ArrayList<FolderBean>();

    private ProgressDialog mProgressDialog;

    private Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0x110) {
                mProgressDialog.dismiss();
                //为gridView设置数据
                data2View();
                //这时候popupwindow的数据集就已经有了
                initPopupWindow();
            }
        }
    };

    /**
     * 初始化popupwindow
     */
    private void initPopupWindow() {
        mPopupWindow = new ImageDirPopupWindow(this, mFolderBeans);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupwindow消失时，开灯
                lightOn();
            }
        });
        mPopupWindow.setOnItemClickListener(this);
    }

    /**
     * 开灯
     */
    private void lightOn() {
        //开灯就是设置window的透明度
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
    }

    /**
     * 关灯
     */
    private void lightOff() {
        //开灯就是设置window的透明度
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
    }

    private ImageDirPopupWindow mPopupWindow;

    /**
     * 绑定数据到gridView
     */
    private void data2View() {
        //gridView的数据集是每个图片的路径,因为保存了currentDir，从中可以拿到每个图片的路径
        if (mCurrentDir == null) {
            Toast.makeText(this, "未扫描到任何图片", Toast.LENGTH_SHORT).show();
            return;
        }
        mImgs = Arrays.asList(addPicturesT0Array(mCurrentDir));
        mImgAdapter = new ImageAdapter(this, mImgs, mCurrentDir.getAbsolutePath());
        mGridView.setAdapter(mImgAdapter);
        mDirCount.setText(mMaxCount + "");
        mDirName.setText(mCurrentDir.getName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mGridView = (GridView) findViewById(R.id.id_gridview);
        mBottomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);
        mDirName = (TextView) findViewById(R.id.id_dir_name);
        mDirCount = (TextView) findViewById(R.id.id_count);
    }

    /**
     * 利用contentProvider去扫描手机中的图片
     */
    private void initData() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "当前存储卡不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
        //开启线程去扫描手机中的图片
        new Thread() {
            @Override
            public void run() {
                //因为使用的是contentprovider，以表的形式提供数据，所以需要一个指向所有图片的uri
                Uri mImgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver cr = MainActivity.this.getContentResolver();
                Cursor cursor = cr.query(mImgUri, null, MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?", new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);
                //用来存放父路径，防止重复遍历
                Set<String> dirPaths = new HashSet<String>();
                while (cursor.moveToNext()) {
                    FolderBean folderBean = null;
                    //得到图片的路径
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    //得到图片的路径后就可以得到它的父路径
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null) {
                        continue;
                    }
                    String parentPath = parentFile.getAbsolutePath();
                    if (dirPaths.contains(parentPath)) {
                        continue;
                    } else {
                        dirPaths.add(parentPath);
                        folderBean = new FolderBean();
                        folderBean.setDir(parentPath);
                        folderBean.setFirstImgPath(path);
                    }
                    //下面遍历父路径中的图片，共有多少张
                    if (parentFile.list() == null) {
                        continue;
                    } else {
                        int picCount = addPicturesT0Array(parentFile).length;
                        folderBean.setCount(picCount);
                        mFolderBeans.add(folderBean);
                        if (picCount > mMaxCount) {
                            mMaxCount = picCount;
                            mCurrentDir = parentFile;
                        }
                    }
                }
                cursor.close();
                //扫描完成后通知UI线程
                mUIHandler.sendEmptyMessage(0x110);
            }
        }.start();


    }

    private void initEvent() {
        mBottomLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.setAnimationStyle(R.style.dir_popwindow_anim);
                mPopupWindow.showAsDropDown(mBottomLy, 0, 0);
                lightOff();
            }
        });
    }


    @Override
    public void onItemClick(View view, int pos) {
        mPopupWindow.dismiss();
        lightOn();
        FolderBean bean = mFolderBeans.get(pos);
        String dirPath = bean.getDir();
        mCurrentDir = new File(dirPath);
        mImgs = Arrays.asList(addPicturesT0Array(mCurrentDir));
        mMaxCount = mImgs.size();
        mImgAdapter = new ImageAdapter(this, mImgs, mCurrentDir.getAbsolutePath());
        mGridView.setAdapter(mImgAdapter);
        mDirCount.setText(mMaxCount + "");
        mDirName.setText(mCurrentDir.getName());

    }

    /**
     * 代码复用
     *
     * @param file
     */
    private String[] addPicturesT0Array(File file) {
        return file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".png")) {
                    return true;
                }
                return false;
            }
        });
    }
}
