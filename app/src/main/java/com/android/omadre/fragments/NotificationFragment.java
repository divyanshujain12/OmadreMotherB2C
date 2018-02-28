package com.android.omadre.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.omadre.Models.NotificationModel;
import com.android.omadre.R;
import com.android.omadre.Utils.ReusedFunctions;
import com.android.omadre.adapters.NotificationAdapter;
import com.android.omadre.databinding.FragmentNotificationBinding;
import com.androidlib.GlobalClasses.BaseFragment;
import com.androidlib.Utils.UniversalParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by divyanshuPC on 2/25/2018.
 */

public class NotificationFragment extends BaseFragment{

    FragmentNotificationBinding fragmentNotificationBinding;
    NotificationAdapter notificationAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentNotificationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);
        fragmentNotificationBinding.setPresenter(this);
        return fragmentNotificationBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<NotificationModel> notificationModels = null;
        try {
            notificationModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(new JSONArray(ReusedFunctions.getInstance().getNotificationSampleJSON()),NotificationModel.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notificationAdapter = new NotificationAdapter(getContext(),notificationModels,this);
        fragmentNotificationBinding.notificatonRV.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentNotificationBinding.notificatonRV.setAdapter(notificationAdapter);

    }
}
