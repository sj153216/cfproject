package com.sjhcn.recyclerview_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjhcn.constants.Constant;
import com.sjhcn.entitis.Item;
import com.sjhcn.module.DataManager;
import com.sjhcn.myfragment.ThirdFragment;
import com.sjhcn.qrcode.R;

import java.util.List;

/**
 * Created by tong on 2016/7/12.
 */
public class ThridFragmentAdapter extends RecyclerView.Adapter<ThirdFragmentViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Item> mData;


    public ThridFragmentAdapter(Context context, List<Item> data) {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(context);


    }

    @Override
    public ThirdFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        ThirdFragmentViewHolder myViewHolder = new ThirdFragmentViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ThirdFragmentViewHolder holder, int position) {
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

class ThirdFragmentViewHolder extends RecyclerView.ViewHolder {

    private DataManager mDataMgr;
    ImageView lableView;
    ImageView arrowView;
    TextView contentView;

    public ThirdFragmentViewHolder(View itemView) {
        super(itemView);
        mDataMgr = DataManager.getInstance();
        lableView = (ImageView) itemView.findViewById(R.id.lable);
        arrowView = (ImageView) itemView.findViewById(R.id.arrow);
        contentView = (TextView) itemView.findViewById(R.id.content);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getLayoutPosition();
                switch (pos) {
                    case 0:
                        break;
                    case 1:
                        mDataMgr.getDataFromLocal(new ThirdFragment(), Constant.ACTION_LOAD_QRCODEINFO);
                        break;
                    case 2:
                        break;
                }
            }
        });

    }
}
