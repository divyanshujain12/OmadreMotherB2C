package com.android.omadre.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.omadre.Models.FeedModel;
import com.android.omadre.R;
import com.android.omadre.databinding.AdapterFeedRecordNotificationBinding;
import com.androidlib.Interfaces.RecyclerViewClick;
import com.androidlib.Utils.ImageLoading;
import com.androidlib.Utils.Utils;

import java.util.ArrayList;

/**
 * Created by divyanshuPC on 3/7/2018.
 */

public class FeedRecordAdapter extends RecyclerView.Adapter<FeedRecordAdapter.MyViewHolder> {

    private ArrayList<FeedModel> feedModels;
    private Context context;
    private ImageLoading imageLoading;
    private RecyclerViewClick recyclerViewClick;
    AdapterFeedRecordNotificationBinding adapterFeedRecordNotificationBinding;
    LinearLayout.LayoutParams layoutParams;

    class MyViewHolder extends RecyclerView.ViewHolder {

        private MyViewHolder(View view) {
            super(view);
        }
    }

    public FeedRecordAdapter(Context context, ArrayList<FeedModel> feedModels, RecyclerViewClick recyclerViewClick) {
        this.feedModels = feedModels;
        this.context = context;
        imageLoading = new ImageLoading(context);
        this.recyclerViewClick = recyclerViewClick;
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5, 5);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_feed_record_notification, parent, false);
        adapterFeedRecordNotificationBinding = DataBindingUtil.bind(itemView);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        FeedModel feedModel = feedModels.get(position);
        adapterFeedRecordNotificationBinding.setData(feedModel);
        adapterFeedRecordNotificationBinding.setUtils(Utils.getInstance());
        adapterFeedRecordNotificationBinding.setPresenter(this);
        adapterFeedRecordNotificationBinding.executePendingBindings();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewClick.onClickItem(holder.getAdapterPosition(), v);
            }
        });
    }


    @Override
    public int getItemCount() {
        return feedModels.size();
    }
}
