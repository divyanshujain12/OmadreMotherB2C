package com.android.omadre.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.Toolbar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityRecordBinding = DataBindingUtil.setContentView(this, R.layout.activity_record);
        activityRecordBinding.setPresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Utils.configureToolbarWithBackButton(this, toolbar, "RECORD");

        initViews();
    }

    private void initViews() {
        enableDisableBothLeftRightBreastBT(true);
        disableBothStartStopBT();

    }

    public void onLeftBreastClick() {
        changeLeftBreastColorEnable(true);
        enableDisableStartStopBT(true);
        leftBreastButtonClick = true;

    }

    public void onRightBreastClick() {
        changeLeftBreastColorEnable(false);
        enableDisableStartStopBT(true);
        leftBreastButtonClick = false;
    }

    public void onStartClick() {
        enableDisableBothLeftRightBreastBT(false);
        enableDisableStartStopBT(false);
        startStopTimer(true);
        enableDisableSubmitAnAttachBarcodeButton(false);
    }

    public void onStopClick() {
        enableDisableBothLeftRightBreastBT(true);
        disableBothStartStopBT();
        changeBothBreastBgColorDisable();
        startStopTimer(false);
    }

    public void onAttachBarcodeClick() {
        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }

    public void onSubmitClick() {
        if (qrCodeData.length() > 0) {
            ReusedFunctions.getInstance().HitJsonObjectWebAPI(this, true, ApiCodes.BOTTLE_INFO, CallWebService.POST, API.BOTTLE_INFO, createJsonForPostRecord(), this);

        } else {
            CustomToasts.getInstance(this).showErrorToast("Please attach barcode first");
        }
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

    private void enableDisableStartStopBT(boolean enableStartBtn) {
        if (enableStartBtn) {
            activityRecordBinding.startBT.setEnabled(true);
            activityRecordBinding.startBT.setBackgroundResource(R.drawable.rounded_button_primary_bg);
            activityRecordBinding.stopBT.setEnabled(false);
            activityRecordBinding.stopBT.setBackgroundResource(R.drawable.rounded_button_secondary_bg);
        } else {
            activityRecordBinding.stopBT.setEnabled(true);
            activityRecordBinding.stopBT.setBackgroundResource(R.drawable.rounded_button_primary_bg);
            activityRecordBinding.startBT.setEnabled(false);
            activityRecordBinding.startBT.setBackgroundResource(R.drawable.rounded_button_secondary_bg);
        }
    }

    private void disableBothStartStopBT() {
        activityRecordBinding.startBT.setEnabled(false);
        activityRecordBinding.startBT.setBackgroundResource(R.drawable.rounded_button_secondary_bg);
        activityRecordBinding.stopBT.setEnabled(false);
        activityRecordBinding.stopBT.setBackgroundResource(R.drawable.rounded_button_secondary_bg);
    }

    private void changeLeftBreastColorEnable(boolean enableLeftBreast) {
        if (enableLeftBreast) {
            activityRecordBinding.leftBreastBT.setBackgroundResource(R.drawable.rounded_button_primary_bg);
            activityRecordBinding.rightBreastBT.setBackgroundResource(R.drawable.rounded_button_secondary_bg);
        } else {
            activityRecordBinding.rightBreastBT.setBackgroundResource(R.drawable.rounded_button_primary_bg);
            activityRecordBinding.leftBreastBT.setBackgroundResource(R.drawable.rounded_button_secondary_bg);
        }
    }

    private void enableDisableBothLeftRightBreastBT(boolean enable) {
        if (enable) {
            activityRecordBinding.leftBreastBT.setEnabled(true);
            activityRecordBinding.rightBreastBT.setEnabled(true);
        } else {
            activityRecordBinding.leftBreastBT.setEnabled(false);
            activityRecordBinding.rightBreastBT.setEnabled(false);
        }
    }

    private void changeBothBreastBgColorDisable() {
        activityRecordBinding.leftBreastBT.setBackgroundResource(R.drawable.rounded_button_secondary_bg);
        activityRecordBinding.rightBreastBT.setBackgroundResource(R.drawable.rounded_button_secondary_bg);
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

    private void enableDisableSubmitAnAttachBarcodeButton(boolean enable) {
        if (enable) {
            activityRecordBinding.submitBT.setBackgroundResource(R.drawable.rounded_button_primary_bg);
            activityRecordBinding.submitBT.setEnabled(true);

            activityRecordBinding.attachBarcodeBT.setEnabled(true);
        } else {
            activityRecordBinding.submitBT.setBackgroundResource(R.drawable.rounded_button_secondary_bg);
            activityRecordBinding.submitBT.setEnabled(false);

            activityRecordBinding.attachBarcodeBT.setEnabled(false);
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
            enableDisableSubmitAnAttachBarcodeButton(true);
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
                activityRecordBinding.attachBarcodeBT.setText(R.string.barcode_attached);
                activityRecordBinding.attachBarcodeBT.setBackgroundResource(R.drawable.rounded_button_primary_bg);

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
