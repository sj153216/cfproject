package com.sjhcn.myfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sjhcn.qrcode.R;

/**
 * Created by sjhcn on 2016/7/12.
 */
public class SecondFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.second_fragment, container, false);
        TextView textView = (TextView) view.findViewById(R.id.tv);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.second_recycler_view);

        return view;
    }


}
