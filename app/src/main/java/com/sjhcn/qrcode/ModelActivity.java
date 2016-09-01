package com.sjhcn.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjhcn.constants.Constant;
import com.sjhcn.recyclerview_adapter.ModelGridViewAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjhcn on 2016/8/20.
 */
public class ModelActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTitle;
    private GridView mModelGv;
    private Button mSelectBt;

    //生成二维码的字符串
    private String mQRcodeStr;
    private String mNameStr;
    private String mPhoneStr;
    private String mPosStr;
    private String mPartStr;
    private String mEmailStr;
    private String mCompanyStr;
    private int action;

    private int[] mBitmaps = new int[]{R.drawable.gexing_2, R.drawable.gexing_3, R.drawable.gexing_4,
            R.drawable.gexing_5, R.drawable.gexing_8, R.drawable.gexing_9,
            R.drawable.gexing_10, R.drawable.gexing_12};
    private List<Drawable> mData = new ArrayList<Drawable>();
    private ModelGridViewAdapter mAdapter;

    private int lastClickPos = 0;
    private boolean isSelected = false;

    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.model_activity);
        initView();
        initData();
        initEvent();

    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.title_name);
        mModelGv = (GridView) findViewById(R.id.model_gridview);
        mSelectBt = (Button) findViewById(R.id.model_bt);
    }

    private void initData() {
        Intent intent = getIntent();
        mNameStr = intent.getStringExtra("mNameStr");
        mPhoneStr = intent.getStringExtra("mPhoneStr");
        mPosStr = intent.getStringExtra("mPosStr");
        mPartStr = intent.getStringExtra("mPartStr");
        mEmailStr = intent.getStringExtra("mEmailStr");
        mCompanyStr = intent.getStringExtra("mCompanyStr");
        mQRcodeStr = intent.getStringExtra("qrCode");
        action = intent.getIntExtra("action", Constant.ACTION_GENERATE_MAP_QRCODEINFO);
        for (int i = 0; i < mBitmaps.length; i++) {
            mData.add(getResources().getDrawable(mBitmaps[i]));
        }
        mAdapter = new ModelGridViewAdapter(mData, this);
        mModelGv.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTitle.setText("选择模板");
    }

    private void initEvent() {
        mSelectBt.setOnClickListener(this);
        mModelGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView selectIv = (ImageView) view.findViewById(R.id.model_item_select_iv);
                if (lastClickPos == position) {
                    // ImageView iv = (ImageView) view.findViewById(R.id.model_item_iv);
                    //若果是，判断是否已经选择过
                    if (!isSelected) {
                        selectIv.setVisibility(View.VISIBLE);
                        isSelected = true;
                        mBitmap = BitmapFactory.decodeResource(ModelActivity.this.getResources(), mBitmaps[position]);
                    } else {
                        selectIv.setVisibility(View.GONE);
                        isSelected = false;
                    }
                } else {
                    if (isSelected) {
                        int count = mModelGv.getChildCount();
                        ImageView oldSelectIv = (ImageView) mModelGv.getChildAt(lastClickPos).findViewById(R.id.model_item_select_iv);
                        if (oldSelectIv != null) {
                            oldSelectIv.setVisibility(View.GONE);
                        }
                    }
                    selectIv.setVisibility(View.VISIBLE);
                    isSelected = true;
                    mBitmap = BitmapFactory.decodeResource(ModelActivity.this.getResources(), mBitmaps[position]);
                }
                lastClickPos = position;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.model_bt:
                startMakeResuleActivity();
                break;
        }
    }

    /**
     * 启动界面
     */
    private void startMakeResuleActivity() {
        Intent intent = new Intent(this, MakeResultActivity.class);
        intent.putExtra("mNameStr", mNameStr);
        intent.putExtra("mPhoneStr", mPhoneStr);
        intent.putExtra("mPosStr", mPosStr);
        intent.putExtra("mPartStr", mPartStr);
        intent.putExtra("mEmailStr", mEmailStr);
        intent.putExtra("mCompanyStr", mCompanyStr);
        intent.putExtra("qrCode", mQRcodeStr);
        intent.putExtra("mapCode", compressBitmap(mBitmap));
        intent.putExtra("action", Constant.ACTION_GENERATE_NAME_MODEL_QRCODEINFO);
        startActivity(intent);
    }

    /**
     * 将bitmap转为byte【】
     *
     * @param bitmap
     * @return
     */
    private byte[] compressBitmap(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] array = outputStream.toByteArray();
        return array;
    }
}
