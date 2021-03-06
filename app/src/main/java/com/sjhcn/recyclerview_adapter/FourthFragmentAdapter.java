package com.sjhcn.recyclerview_adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sjhcn.application.QRcodeApplication;
import com.sjhcn.entitis.Item;
import com.sjhcn.module.DataManager;
import com.sjhcn.myfragment.ThirdFragment;
import com.sjhcn.qrcode.AboutUsActivity;
import com.sjhcn.qrcode.R;
import com.sjhcn.qrcode.SuggestionActivity;
import com.sjhcn.utils.Utils;
import com.sjhcn.view.MyDialog;

import java.util.List;

import cn.sharesdk.framework.Platform;

/**
 * Created by sjhcn on 2016/7/20.
 */
public class FourthFragmentAdapter extends RecyclerView.Adapter<FourthFragmentViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Item> mData;


    public FourthFragmentAdapter(Context context, List<Item> data) {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(context);


    }

    @Override
    public FourthFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        FourthFragmentViewHolder myViewHolder = new FourthFragmentViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(FourthFragmentViewHolder holder, int position) {
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

class FourthFragmentViewHolder extends RecyclerView.ViewHolder {

    private DataManager mDataMgr;
    ImageView lableView;
    ImageView arrowView;
    TextView contentView;

    public FourthFragmentViewHolder(View itemView) {
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
                        Intent aboutIntent = new Intent(ThirdFragment.mActivity, AboutUsActivity.class);
                        ThirdFragment.mActivity.startActivity(aboutIntent);
                        break;
                    case 1:
                        final MyDialog mDialog = new MyDialog(ThirdFragment.mActivity);
                        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        mDialog.setContentView(R.layout.dialog_progress_update);
                        mDialog.setCancelable(true);
                        mDialog.setCanceledOnTouchOutside(true);
                        mDialog.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                                Toast.makeText(QRcodeApplication.getInstance(), "当前版本已是最新", Toast.LENGTH_SHORT).show();
                            }
                        }, 2000);

                        break;
                    case 2:
                        Utils.showShare(Platform.SHARE_WEBPAGE, null);
                        break;
                    case 3:
                        //意见反馈
                        Intent suggestionIsntent = new Intent(ThirdFragment.mActivity, SuggestionActivity.class);
                        ThirdFragment.mActivity.startActivity(suggestionIsntent);
                        ;
                        break;
                }
            }
        });

    }

}


