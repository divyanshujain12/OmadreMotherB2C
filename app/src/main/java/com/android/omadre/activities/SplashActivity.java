package com.android.omadre.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.android.omadre.Constants.Constants;
import com.android.omadre.R;
import com.android.omadre.Utils.MySharedPereference;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //checkNewVersion();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = null;
                if (!MySharedPereference.getInstance().getBoolean(SplashActivity.this, Constants.IS_LOGGED_IN)) {
                    i = new Intent(SplashActivity.this, IntroActivity.class);

                } else {
                    i = new Intent(SplashActivity.this, BottomNavigationActivity.class);
                    // i = new Intent(SplashActivity.this, BottomTabActivity.class);
                }
                startActivity(i);
                finish();
            }
        }, 3000);

    }

    Handler handler = new Handler();

}