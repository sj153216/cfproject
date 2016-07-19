package com.sjhcn.recyclerview_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjhcn.entitis.RecordItem;
import com.sjhcn.qrcode.R;

import java.util.List;

/**
 * Created by tong on 2016/7/14.
 */
public class ScanRecordAdapter extends RecyclerView.Adapter<ScanViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<RecordItem> mData;

    public ScanRecordAdapter(Context context, List<RecordItem> data) {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public ScanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.scan_record_recyclerview_item, parent, false);
        ScanViewHolder scanViewHolder = new ScanViewHolder(view);
        return scanViewHolder;
    }

    @Override
    public void onBindViewHolder(ScanViewHolder holder, int position) {
        RecordItem item = mData.get(position);
        holder.lableView.setImageBitmap(item.getLable());
        holder.arrowView.setImageBitmap(item.getArrow());
        holder.scanRecordView.setText(item.getQrcode());
        holder.timeView.setText(item.getTime());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

}

class ScanViewHolder extends RecyclerView.ViewHolder {

    ImageView lableView;
    ImageView arrowView;
    TextView scanRecordView;
    TextView timeView;

    public ScanViewHolder(View itemView) {
        super(itemView);
        lableView = (ImageView) itemView.findViewById(R.id.lable);
        arrowView = (ImageView) itemView.findViewById(R.id.arrow);
        scanRecordView = (TextView) itemView.findViewById(R.id.qrcode);
        timeView = (TextView) itemView.findViewById(R.id.time);


    }

}
