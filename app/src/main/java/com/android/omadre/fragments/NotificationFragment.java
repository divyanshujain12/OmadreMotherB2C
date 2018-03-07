package com.android.omadre.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.omadre.R;
import com.android.omadre.adapters.ViewPagerAdapter;
import com.android.omadre.databinding.FragmentNotificationBinding;
import com.android.omadre.fragments.notificationFragments.FeedRecordFragment;
import com.android.omadre.fragments.notificationFragments.PumpRecordFragment;
import com.androidlib.GlobalClasses.BaseFragment;
import com.androidlib.Utils.NonSwipeableViewPager;

/**
 * Created by divyanshuPC on 2/25/2018.
 */

public class NotificationFragment extends BaseFragment{

    FragmentNotificationBinding fragmentNotificationBinding;

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
        initViews();

    }

    private void initViews() {
        setupViewPager(fragmentNotificationBinding.notificationVP);
        fragmentNotificationBinding.tabs.setupWithViewPager(fragmentNotificationBinding.notificationVP);

    }

    private void setupViewPager(NonSwipeableViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FeedRecordFragment(), "Feeds");
        adapter.addFragment(new PumpRecordFragment(), "Records");

        viewPager.setAdapter(adapter);
    }
}
