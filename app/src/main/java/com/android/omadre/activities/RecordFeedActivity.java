package com.android.omadre.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.omadre.Constants.API;
import com.android.omadre.Constants.ApiCodes;
import com.android.omadre.Constants.Constants;
import com.android.omadre.R;
import com.android.omadre.Utils.MySharedPereference;
import com.android.omadre.Utils.ReusedFunctions;
import com.android.omadre.databinding.ActivityRecordFeedBinding;
import com.androidlib.CustomViews.CustomToasts;
import com.androidlib.GlobalClasses.BaseActivity;
import com.androidlib.Utils.CallWebService;
import com.androidlib.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by divyanshuPC on 3/7/2018.
 */

public class RecordFeedActivity extends BaseActivity {
    public ActivityRecordFeedBinding activityRecordFeedBinding;
    private long startTime, stopTime;
    private boolean recording = false;
    long timeWhenStopped = 0;
    long totalPausedTime = 0;
    long pausedAt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityRecordFeedBinding = DataBindingUtil.setContentView(this, R.layout.activity_record_feed);
        activityRecordFeedBinding.setPresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Utils.configureToolbarWithBackButton(this, toolbar, "RECORD FEEDING");

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
        activityRecordFeedBinding.startPauseIV.setImageResource(R.drawable.ic_pause);
        recording = true;
        if (timeWhenStopped == 0) {
            activityRecordFeedBinding.timerCM.setBase(SystemClock.elapsedRealtime());
            startTime = Utils.getCurrentTimeInMillisecond();
            stopTime = 0;
        } else {
            long pausedTimeDifference = Utils.getCurrentTimeInMillisecond() - pausedAt;
            totalPausedTime += pausedTimeDifference;
            activityRecordFeedBinding.timerCM.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        }
        activityRecordFeedBinding.timerCM.start();

    }

    private void onTimePause() {
        activityRecordFeedBinding.startPauseIV.setImageResource(R.drawable.ic_play);
        recording = false;
        timeWhenStopped = activityRecordFeedBinding.timerCM.getBase() - SystemClock.elapsedRealtime();
        activityRecordFeedBinding.timerCM.stop();
        pausedAt = Utils.getCurrentTimeInMillisecond();
    }

    public void onStopTimeClick() {
        stopTime = Utils.getCurrentTimeInMillisecond() - totalPausedTime;
        stopFeeding();
    }

    public void onSaveClick() {
        if (startTime > 0 && stopTime > 0) {
            ReusedFunctions.getInstance().HitJsonObjectWebAPI(this, true, ApiCodes.FEED, CallWebService.POST, API.FEED, createJsonForRecordFeeding(), this);
        } else {
            Toast.makeText(this, "Please Record Feeding Session First!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);
        if (response.has("Feeds")) {
            CustomToasts.getInstance(this).showSuccessToast("Record Created Successfully!");
            Intent intent = new Intent(this,BottomNavigationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            CustomToasts.getInstance(this).showErrorToast("error in creating feed record");
        }
    }

    @Override
    public void onFailure(String str, int apiType) {
        super.onFailure(str, apiType);
    }

    public void onResetClick() {
        stopFeeding();
        pausedAt = 0;
        totalPausedTime = 0;
        startTime = 0;
        stopTime = 0;
    }

    private void stopFeeding() {
        activityRecordFeedBinding.timerCM.setBase(SystemClock.elapsedRealtime());
        activityRecordFeedBinding.timerCM.stop();
        activityRecordFeedBinding.startPauseIV.setImageResource(R.drawable.ic_play);
        recording = false;
        timeWhenStopped = 0;
    }

    private JSONObject createJsonForRecordFeeding() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.START_TIME, startTime);
            jsonObject.put(Constants.END_TIME, stopTime);
            jsonObject.put(Constants.NOTES, activityRecordFeedBinding.notesET.getText().toString());
            jsonObject.put(Constants.MOTHERID, MySharedPereference.getInstance().getString(this, Constants.MOTHER_ID));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}


