package com.android.omadre.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.omadre.Constants.API;
import com.android.omadre.Constants.ApiCodes;
import com.android.omadre.Constants.Constants;
import com.android.omadre.R;
import com.android.omadre.Utils.FeedingAmountInterface;
import com.android.omadre.Utils.MySharedPereference;
import com.android.omadre.Utils.ReusedFunctions;
import com.android.omadre.databinding.ActivityRecordBinding;
import com.androidlib.CustomViews.CustomToasts;
import com.androidlib.GlobalClasses.BaseActivity;
import com.androidlib.Utils.CallWebService;
import com.androidlib.Utils.Utils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class RecordActivity extends BaseActivity implements FeedingAmountInterface {

    ActivityRecordBinding activityRecordBinding;
    private boolean leftBreastButtonClick;
    private long startTime, stopTime;
    private long leftStartTime, leftStopTime, rightStartTime, rightStopTime;
    private String leftAmount, rightAmount;
    private IntentIntegrator intentIntegrator;
    private String qrCodeData;
    private boolean recording = false;
    private String TIME_FORMAT = "hh:mm:ss";

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
        activityRecordBinding.startTimeTV.setText(Utils.formatDateAndTime(Utils.getCurrentTimeInMillisecond(), Utils.TIME_FORMAT));
    }

    public void onStartRecordClick() {
        if (!recording) {
           // onTimeStart();
            startActivity(new Intent(this,CreateRecordActivity.class));
        } else {
            onStopTime();
        }
        startStopTimer(recording);
    }

    private void onTimeStart() {
        recording = true;
        activityRecordBinding.timesCL.setVisibility(View.VISIBLE);
        startTime = Utils.getCurrentTimeInMillisecond();
        activityRecordBinding.startTimeTV.setText(Utils.formatDateAndTime(startTime, TIME_FORMAT));
        activityRecordBinding.startStopBT.setBackgroundResource(R.drawable.circle_button_secondary_bg);
        activityRecordBinding.startStopBT.setText(getString(R.string.stop));
    }

    private void onStopTime() {
        recording = false;
        stopTime = Utils.getCurrentTimeInMillisecond();
        activityRecordBinding.stopTimeTV.setText(Utils.formatDateAndTime(stopTime, TIME_FORMAT));
        activityRecordBinding.startStopBT.setBackgroundResource(R.drawable.circle_button_primary_bg);
        activityRecordBinding.startStopBT.setText(getString(R.string.start));
        activityRecordBinding.durationTV.setText(Utils.getDifferenceInString(stopTime - startTime));
    }


    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);
        if (response.has("success") && response.getBoolean("success"))
            CustomToasts.getInstance(this).showSuccessToast("Bottle Created Successfully");
        else
            CustomToasts.getInstance(this).showErrorToast("error in creating record");
        finish();
    }

    @Override
    public void onFailure(String str, int apiType) {
        super.onFailure(str, apiType);
        CustomToasts.getInstance(this).showErrorToast(str);
    }

    private void startStopTimer(boolean start) {
        if (start) {
            activityRecordBinding.timerCM.setBase(SystemClock.elapsedRealtime());
            activityRecordBinding.timerCM.start();
            startTime = Calendar.getInstance().getTimeInMillis();
        } else {
            activityRecordBinding.timerCM.setBase(SystemClock.elapsedRealtime());
            activityRecordBinding.timerCM.stop();
            stopTime = Calendar.getInstance().getTimeInMillis();


            ReusedFunctions.getInstance().showMilkQuantityAlert(this, this);
        }
    }

    @Override
    public void FeedingAmount(String amount) {
        saveValuesInVariables(amount);
    }

    private void saveValuesInVariables(String amount) {
        if (leftBreastButtonClick) {
            leftStartTime = startTime;
            leftStopTime = stopTime;
            leftAmount = amount;
        } else {
            rightStartTime = startTime;
            rightStopTime = stopTime;
            rightAmount = amount;
        }
        if (leftStartTime > 0 && rightStartTime > 0) {
            //enableDisableSubmitAnAttachBarcodeButton(true);
        }
        startTime = 0;
        stopTime = 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                CustomToasts.getInstance(this).showErrorToast("Result Not Found");
            } else {
                qrCodeData = result.getContents();


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private JSONObject createJsonForPostRecord() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.LEFT_AMOUNT, leftAmount);
            jsonObject.put(Constants.RIGHT_AMOUNT, rightAmount);
            jsonObject.put(Constants.LEFT_START_TIMING, leftStartTime);
            jsonObject.put(Constants.LEFT_STOP_TIMING, leftStopTime);
            jsonObject.put(Constants.RIGHT_START_TIMING, rightStartTime);
            jsonObject.put(Constants.RIGHT_STOP_TIMING, rightStopTime);
            jsonObject.put(Constants.MOTHERID, MySharedPereference.getInstance().getString(this, Constants.MOTHER_ID));
            jsonObject.put(Constants.GENERATED_QR_CODE_ID, qrCodeData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


}
