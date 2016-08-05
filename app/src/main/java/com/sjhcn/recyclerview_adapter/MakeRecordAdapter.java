package com.sjhcn.recyclerview_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjhcn.entitis.MakeRecordItem;
import com.sjhcn.qrcode.R;

import java.util.List;

/**
 * Created by sjhcn on 2016/8/4.
 */
public class MakeRecordAdapter extends RecyclerView.Adapter<MakeViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<MakeRecordItem> mData;

    public MakeRecordAdapter(Context context, List<MakeRecordItem> data) {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public MakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.make_record_recyclerview_item, parent, false);
        MakeViewHolder MakeViewHolder = new MakeViewHolder(view);
        return MakeViewHolder;
    }

    @Override
    public void onBindViewHolder(MakeViewHolder holder, int position) {
        MakeRecordItem item = mData.get(position);
        holder.lableView.setImageBitmap(item.getLable());
        holder.arrowView.setImageBitmap(item.getArrow());
        holder.makeRecordView.setText(item.getQrcode());
        holder.timeView.setText(item.getTime());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

}

class MakeViewHolder extends RecyclerView.ViewHolder {

    ImageView lableView;
    ImageView arrowView;
    TextView makeRecordView;
    TextView timeView;

    public MakeViewHolder(View itemView) {
        super(itemView);
        lableView = (ImageView) itemView.findViewById(R.id.lable);
        arrowView = (ImageView) itemView.findViewById(R.id.arrow);
        makeRecordView = (TextView) itemView.findViewById(R.id.qrcode);
        timeView = (TextView) itemView.findViewById(R.id.time);

    }

}

