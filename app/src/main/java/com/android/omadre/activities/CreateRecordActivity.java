package com.android.omadre.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RadioGroup;

import com.android.omadre.Constants.API;
import com.android.omadre.Constants.ApiCodes;
import com.android.omadre.Constants.Constants;
import com.android.omadre.R;
import com.android.omadre.Utils.MySharedPereference;
import com.android.omadre.Utils.ReusedFunctions;
import com.android.omadre.databinding.ActivityCreateRecordBinding;
import com.androidlib.CustomViews.CustomToasts;
import com.androidlib.GlobalClasses.BaseActivity;
import com.androidlib.Utils.CallWebService;
import com.androidlib.Utils.Utils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateRecordActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private ActivityCreateRecordBinding activityCreateRecordBinding;
    boolean left, right, both;
    private IntentIntegrator intentIntegrator;
    private String qrCodeData;
    private String totalAmount = "0";
    private long startTime, stopTime;
    private JSONObject jsonObject = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityCreateRecordBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_record);
        activityCreateRecordBinding.setPresenter(this);
        initViews();
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Utils.configureToolbarWithBackButton(this, toolbar, "CREATE RECORD");
        startTime = getIntent().getLongExtra(Constants.START_TIME, 0);
        stopTime = getIntent().getLongExtra(Constants.END_TIME, 0);
        activityCreateRecordBinding.breastRG.setOnCheckedChangeListener(this);
    }

    public void onBarcodeClick() {
        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();

    }

    public void onSubmitClick() {

        totalAmount = activityCreateRecordBinding.totalMilkET.getText().toString();
        String errorMsg = "";
        if (totalAmount.equals(""))
            errorMsg = "Please Enter Milk Quantity";
        else if (qrCodeData.equals(""))
            errorMsg = "Please scan barcode first";


        if (errorMsg.equals(""))
        ReusedFunctions.getInstance().HitJsonObjectWebAPI(this, true, ApiCodes.CREATE_RECORD, CallWebService.POST, API.BOTTLE_INFO, createJsonForPostRecord(), this);
        else
            CustomToasts.getInstance(this).showErrorToast(errorMsg);
    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);
        if (response.has("success") && response.getBoolean("success"))
            CustomToasts.getInstance(this).showSuccessToast("Bottle Created Successfully");
        else
            CustomToasts.getInstance(this).showErrorToast("error in creating record");
        Intent intent = new Intent(this, BottomNavigationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(String str, int apiType) {
        super.onFailure(str, apiType);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.leftRB:
                left = true;
                right = false;
                both = false;
                break;
            case R.id.rightRB:
                left = false;
                right = true;
                both = false;
                break;
            case R.id.bothRB:
                left = false;
                right = false;
                both = true;
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                CustomToasts.getInstance(this).showErrorToast("Result Not Found");
            } else {
                qrCodeData = result.getContents();
                activityCreateRecordBinding.attachBarcodeBT.setText("Barcode Attached");
                activityCreateRecordBinding.attachBarcodeBT.setBackgroundResource(R.drawable.rounded_green_fill_bg);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private JSONObject createJsonForPostRecord() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.LEFT_AMOUNT, totalAmount);
            jsonObject.put(Constants.LEFT_START_TIMING, startTime);
            jsonObject.put(Constants.LEFT_STOP_TIMING, stopTime);
            jsonObject.put(Constants.NOTES, activityCreateRecordBinding.notesET.getText().toString());
            jsonObject.put(Constants.MOTHERID, MySharedPereference.getInstance().getString(this, Constants.MOTHER_ID));
            jsonObject.put(Constants.GENERATED_QR_CODE_ID, qrCodeData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


}
