package com.sjhcn.recyclerview_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjhcn.entitis.Item;
import com.sjhcn.qrcode.R;

import java.util.List;

/**
 * Created by tong on 2016/7/12.
 */
public class SimpleAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Item> mData;

    public SimpleAdapter(Context context, List<Item> data) {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item item = mData.get(position);
        holder.lableView.setImageBitmap(item.getLable());
        holder.arrowView.setImageBitmap(item.getArrow());
        holder.contentView.setText(item.getContent());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView lableView;
    ImageView arrowView;
    TextView contentView;

    public MyViewHolder(View itemView) {
        super(itemView);
        lableView = (ImageView) itemView.findViewById(R.id.lable);
        arrowView = (ImageView) itemView.findViewById(R.id.arrow);
        contentView = (TextView) itemView.findViewById(R.id.content);

    }
}
