package com.example.imageloader.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.imageloader.R;
import com.example.imageloader.activity.ImageLoader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sjhcn on 2016/9/9.
 */
public class ImageAdapter extends BaseAdapter {
    private static final String TAG = "ImageAdapter";

    public static Set<String> mSelectedImg = new HashSet<String>();

    private List<String> mData;
    private LayoutInflater mInflater;
    private String mDirPath;

    public ImageAdapter(Context context, List<String> data, String dirPath) {
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
        mDirPath = dirPath;

    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            Log.d(TAG, "convertView是null");
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_gridview, parent, false);
            holder.mImg = (ImageView) convertView.findViewById(R.id.id_item_image);
            holder.mSelect = (ImageButton) convertView.findViewById(R.id.id_item_select);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //重置状态
        holder.mImg.setImageResource(R.drawable.pic_no);
        holder.mSelect.setImageResource(R.drawable.btn_check_buttonless_off);
        holder.mImg.setColorFilter(null);
        ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(mDirPath + "/" + mData.get(position), holder.mImg);
        final String imgPath = mDirPath + "/" + mData.get(position);
        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedImg.contains(imgPath)) {
                    //若已经点击过了
                    mSelectedImg.remove(imgPath);
                    holder.mImg.setColorFilter(null);
                    holder.mSelect.setImageResource(R.drawable.btn_check_buttonless_off);
                } else {
                    mSelectedImg.add(imgPath);
                    holder.mImg.setColorFilter(Color.parseColor("#77000000"));
                    holder.mSelect.setImageResource(R.drawable.btn_check_buttonless_on);
                }
            }
        });
        if (mSelectedImg.contains(imgPath)) {
            holder.mImg.setColorFilter(Color.parseColor("#77000000"));
            holder.mSelect.setImageResource(R.drawable.btn_check_buttonless_on);
        }
        return convertView;

    }

    class ViewHolder {
        ImageView mImg;
        ImageButton mSelect;
    }
}
