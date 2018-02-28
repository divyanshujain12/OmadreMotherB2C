package com.android.omadre.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.omadre.Models.NotificationModel;
import com.android.omadre.R;
import com.android.omadre.databinding.AdapterNotificationBinding;
import com.androidlib.Interfaces.RecyclerViewClick;
import com.androidlib.Utils.ImageLoading;

import java.util.ArrayList;

/**
 * Created by divyanshuPC on 2/25/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private ArrayList<NotificationModel> notificationModels;
    private Context context;
    private ImageLoading imageLoading;
    private RecyclerViewClick recyclerViewClick;
    AdapterNotificationBinding adapterNotificationBinding;
    LinearLayout.LayoutParams layoutParams;

    class MyViewHolder extends RecyclerView.ViewHolder {

        private MyViewHolder(View view) {
            super(view);
        }
    }

    public NotificationAdapter(Context context, ArrayList<NotificationModel> notificationModels, RecyclerViewClick recyclerViewClick) {
        this.notificationModels = notificationModels;
        this.context = context;
        imageLoading = new ImageLoading(context);
        this.recyclerViewClick = recyclerViewClick;
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5, 5);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_notification, parent, false);
        adapterNotificationBinding = DataBindingUtil.bind(itemView);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        NotificationModel mailModel = notificationModels.get(position);
        adapterNotificationBinding.setData(mailModel);
        adapterNotificationBinding.executePendingBindings();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewClick.onClickItem(holder.getAdapterPosition(), v);
            }
        });
    }


    @Override
    public int getItemCount() {
        return notificationModels.size();
    }
}



