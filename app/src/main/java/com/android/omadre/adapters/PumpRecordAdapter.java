package com.android.omadre.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.omadre.Models.BottleInfoModel;
import com.android.omadre.R;

import com.android.omadre.databinding.PumpRecordAdapterBinding;
import com.androidlib.Interfaces.RecyclerViewClick;
import com.androidlib.Utils.ImageLoading;
import com.androidlib.Utils.Utils;

import java.util.ArrayList;

/**
 * Created by divyanshuPC on 3/4/2018.
 */

public class PumpRecordAdapter extends RecyclerView.Adapter<PumpRecordAdapter.MyViewHolder> {

    private ArrayList<BottleInfoModel> bottleInfoModels;
    private Context context;
    private ImageLoading imageLoading;
    private RecyclerViewClick recyclerViewClick;
    PumpRecordAdapterBinding pumpRecordAdapterBinding;
    LinearLayout.LayoutParams layoutParams;

    class MyViewHolder extends RecyclerView.ViewHolder {

        private MyViewHolder(View view) {
            super(view);
        }
    }

    public PumpRecordAdapter(Context context, ArrayList<BottleInfoModel> bottleInfoModels, RecyclerViewClick recyclerViewClick) {
        this.bottleInfoModels = bottleInfoModels;
        this.context = context;
        imageLoading = new ImageLoading(context);
        this.recyclerViewClick = recyclerViewClick;
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5, 5);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pump_record_adapter, parent, false);
        pumpRecordAdapterBinding = DataBindingUtil.bind(itemView);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        BottleInfoModel bottleInfoModel = bottleInfoModels.get(position);
        pumpRecordAdapterBinding.setData(bottleInfoModel);
        pumpRecordAdapterBinding.setUtils(Utils.getInstance());
        pumpRecordAdapterBinding.setPresenter(this);
        pumpRecordAdapterBinding.executePendingBindings();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewClick.onClickItem(holder.getAdapterPosition(), v);
            }
        });
    }


    @Override
    public int getItemCount() {
        return bottleInfoModels.size();
    }
}





