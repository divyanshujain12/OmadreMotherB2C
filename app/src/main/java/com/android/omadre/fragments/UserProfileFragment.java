package com.android.omadre.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.omadre.R;
import com.android.omadre.activities.BottomNavigationActivity;
import com.android.omadre.databinding.FragmentProfileBinding;
import com.androidlib.GlobalClasses.BaseFragment;

/**
 * Created by divyanshuPC on 2/25/2018.
 */

public class UserProfileFragment extends BaseFragment{
FragmentProfileBinding fragmentProfileBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        fragmentProfileBinding.setPresenter(this);
        return fragmentProfileBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
