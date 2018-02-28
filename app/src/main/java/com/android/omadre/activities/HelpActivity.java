package com.android.omadre.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.android.omadre.R;
import com.android.omadre.databinding.ActivityHelpBinding;
import com.androidlib.GlobalClasses.BaseActivity;
import com.androidlib.Utils.Utils;

public class HelpActivity extends BaseActivity {

    ActivityHelpBinding activityHelpBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


        activityHelpBinding = DataBindingUtil.setContentView(this, R.layout.activity_help);
        activityHelpBinding.setPresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Utils.configureToolbarWithBackButton(this,toolbar,"HELP");
    }
}
