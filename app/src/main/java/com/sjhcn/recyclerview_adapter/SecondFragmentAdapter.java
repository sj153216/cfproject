package com.sjhcn.recyclerview_adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjhcn.entitis.Item;
import com.sjhcn.module.DataManager;
import com.sjhcn.myfragment.ThirdFragment;
import com.sjhcn.qrcode.MapCardActivity;
import com.sjhcn.qrcode.NameCardActivity;
import com.sjhcn.qrcode.PhoneCardActivity;
import com.sjhcn.qrcode.R;
import com.sjhcn.qrcode.UrlCardActivity;

import java.util.List;

/**
 * Created by sjhcn on 2016/7/20.
 */
public class SecondFragmentAdapter extends RecyclerView.Adapter<SecondFragmentViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Item> mData;


    public SecondFragmentAdapter(Context context, List<Item> data) {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(context);


    }


    @Override
    public SecondFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        SecondFragmentViewHolder myViewHolder = new SecondFragmentViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(SecondFragmentViewHolder holder, int position) {
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

class SecondFragmentViewHolder extends RecyclerView.ViewHolder {

    private DataManager mDataMgr;
    ImageView lableView;
    ImageView arrowView;
    TextView contentView;

    public SecondFragmentViewHolder(View itemView) {
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
                        Intent nmeIntent = new Intent(ThirdFragment.mActivity, NameCardActivity.class);
                        ThirdFragment.mActivity.startActivity(nmeIntent);

                        break;
                    case 1:
                        Intent phoneIntent = new Intent(ThirdFragment.mActivity, PhoneCardActivity.class);
                        ThirdFragment.mActivity.startActivity(phoneIntent);
                        break;
                    case 2:
                        break;
                    case 3:
                        Intent urlIntent = new Intent(ThirdFragment.mActivity, UrlCardActivity.class);
                        ThirdFragment.mActivity.startActivity(urlIntent);
                        break;
                    case 4:
                        Intent mapIntent = new Intent(ThirdFragment.mActivity, MapCardActivity.class);
                        ThirdFragment.mActivity.startActivity(mapIntent);
                        break;
                    case 5:
                        break;
                }
            }
        });

    }

    /**
     * 点击item时设置背景颜色
     *
     * @param view
     */
    private void touchEvent(final View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        view.setBackgroundColor(Color.parseColor("#A473AA"));

                        break;
                    case MotionEvent.ACTION_UP:
                        view.setBackgroundColor(Color.RED);
                        break;

                }
                return false;
            }
        });
    }
}

