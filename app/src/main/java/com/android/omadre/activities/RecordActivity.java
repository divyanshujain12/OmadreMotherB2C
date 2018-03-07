package com.android.omadre.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.omadre.Constants.Constants;
import com.android.omadre.R;
import com.android.omadre.Utils.MySharedPereference;
import com.android.omadre.databinding.ActivityRecordBinding;
import com.androidlib.GlobalClasses.BaseActivity;
import com.androidlib.Utils.Utils;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONException;
import org.json.JSONObject;

public class RecordActivity extends BaseActivity {

    ActivityRecordBinding activityRecordBinding;
    private long startTime, stopTime;
    private boolean recording = false;
    long timeWhenStopped = 0;
    long totalPausedTime = 0;
    long pausedAt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityRecordBinding = DataBindingUtil.setContentView(this, R.layout.activity_record);
        activityRecordBinding.setPresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Utils.configureToolbarWithBackButton(this, toolbar, "RECORD PUMPING");

        initViews();
    }

    private void initViews() {

    }

    public void onStartPauseClick() {
        if (!recording) {
            onTimeStart();
        } else {
            onTimePause();
        }
    }

    private void onTimeStart() {
        activityRecordBinding.startPauseIV.setImageResource(R.drawable.ic_pause);
        recording = true;
        if (timeWhenStopped == 0) {
            activityRecordBinding.timerCM.setBase(SystemClock.elapsedRealtime());
            startTime = Utils.getCurrentTimeInMillisecond();
            stopTime = 0;
        } else {
            long pausedTimeDifference = Utils.getCurrentTimeInMillisecond() - pausedAt;
            totalPausedTime += pausedTimeDifference;
            activityRecordBinding.timerCM.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        }
        activityRecordBinding.timerCM.start();

    }

    private void onTimePause() {
        activityRecordBinding.startPauseIV.setImageResource(R.drawable.ic_play);
        recording = false;
        timeWhenStopped = activityRecordBinding.timerCM.getBase() - SystemClock.elapsedRealtime();
        activityRecordBinding.timerCM.stop();
        pausedAt = Utils.getCurrentTimeInMillisecond();
    }

    public void onStopTimeClick() {
        stopTime = Utils.getCurrentTimeInMillisecond() - totalPausedTime;
        stopPumping();
    }

    public void onSaveClick() {
        if (startTime > 0 && stopTime > 0) {
            Intent intent = new Intent(this, CreateRecordActivity.class);
            intent.putExtra(Constants.START_TIME, startTime);
            intent.putExtra(Constants.END_TIME, stopTime);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please Record Pumping Session First!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onResetClick() {
        stopPumping();
        pausedAt = 0;
        totalPausedTime = 0;
        startTime = 0;
        stopTime = 0;
    }

    private void stopPumping() {
        activityRecordBinding.timerCM.setBase(SystemClock.elapsedRealtime());
        activityRecordBinding.timerCM.stop();
        activityRecordBinding.startPauseIV.setImageResource(R.drawable.ic_play);
        recording = false;
        timeWhenStopped = 0;
    }


}
