package com.sjhcn.recyclerview_adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sjhcn.qrcode.R;

import java.util.List;

/**
 * Created by sjhcn on 2016/8/20.
 */
public class ModelGridViewAdapter extends BaseAdapter {
    private List<Drawable> mData;
    private Context mContext;


    public ModelGridViewAdapter(List<Drawable> data, Context context) {
        this.mData = data;
        this.mContext = context;
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
        final MyViewholder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.model_gridview_item, null);
            holder = new MyViewholder();
            holder.mBitmapIv = (ImageView) convertView.findViewById(R.id.model_item_iv);
            holder.mSelectIv = (ImageView) convertView.findViewById(R.id.model_item_select_iv);
            convertView.setTag(holder);
        } else {
            holder = (MyViewholder) convertView.getTag();
        }
        holder.mBitmapIv.setImageDrawable(mData.get(position));
        return convertView;
    }

    class MyViewholder {
        ImageView mBitmapIv;
        ImageView mSelectIv;
    }
}