package com.android.omadre.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.android.omadre.R;
import com.android.omadre.databinding.ActivityIntroBinding;
import com.androidlib.GlobalClasses.BaseActivity;

public class IntroActivity extends BaseActivity {
    ActivityIntroBinding activityIntroBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        activityIntroBinding = DataBindingUtil.setContentView(this, R.layout.activity_intro);
        activityIntroBinding.setPresenter(this);

    }

    public void onLoginClick() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void onSignupClick() {
        startActivity(new Intent(this, SignupActivity.class));
    }
}
