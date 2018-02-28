package com.android.omadre.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.omadre.R;
import com.android.omadre.activities.BottomNavigationActivity;
import com.android.omadre.activities.FeedActivity;
import com.android.omadre.activities.HelpActivity;
import com.android.omadre.activities.OrderBarcodeActivity;
import com.android.omadre.activities.RecordActivity;
import com.android.omadre.activities.TutorialActivity;
import com.android.omadre.databinding.HomeFragmentBinding;
import com.androidlib.GlobalClasses.BaseFragment;

/**
 * Created by divyanshuPC on 2/14/2018.
 */

public class HomeFragment extends BaseFragment {

    private HomeFragmentBinding homeFragmentBinding;
    private BottomNavigationActivity navigationDrawerActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        homeFragmentBinding.setPresenter(this);
        return homeFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navigationDrawerActivity = (BottomNavigationActivity) getActivity();
        InitViews();
    }

    private void InitViews() {

    }

    public void onCreateBarcodeClick() {
        startActivity(new Intent(getContext(), OrderBarcodeActivity.class));
    }

    public void onRecordClick() {
        startActivity(new Intent(getContext(), RecordActivity.class));
    }

    public void onFeedClick() {
        startActivity(new Intent(getContext(), FeedActivity.class));
    }

    public void onStatisticsClick() {
        //startActivity(new Intent(getContext(), RecordActivity.class));
    }

    public void onHelpClick() {
        startActivity(new Intent(getContext(), HelpActivity.class));
    }

    public void onTutorialClick() {
        startActivity(new Intent(getContext(), TutorialActivity.class));
    }
}
