package com.android.omadre.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.android.omadre.Constants.API;
import com.android.omadre.Constants.ApiCodes;
import com.android.omadre.Constants.Constants;
import com.android.omadre.R;
import com.android.omadre.Utils.MySharedPereference;
import com.android.omadre.Utils.ReusedFunctions;
import com.android.omadre.databinding.ActivityCreateRecordBinding;
import com.androidlib.CustomFontViews.CustomEditTextRegular;
import com.androidlib.CustomViews.CustomAlertDialogs;
import com.androidlib.CustomViews.CustomToasts;
import com.androidlib.GlobalClasses.BaseActivity;
import com.androidlib.Utils.CallWebService;
import com.androidlib.Utils.Utils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class CreateRecordActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnKeyListener {

    private ActivityCreateRecordBinding activityCreateRecordBinding;
    boolean left, right, both;
    private IntentIntegrator intentIntegrator;
    private String qrCodeData;
    private String leftAmount = "0", rightAmount = "0";
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
        activityCreateRecordBinding.totalMilkET.setOnKeyListener(this);
        activityCreateRecordBinding.leftBreastMilkET.setOnKeyListener(this);
        activityCreateRecordBinding.rightBreastMilkET.setOnKeyListener(this);
    }

    public void onBarcodeClick() {
        intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();

    }

    public void onSubmitClick() {

        if (both) {
            leftAmount = activityCreateRecordBinding.leftBreastMilkET.getText().toString();
            rightAmount = activityCreateRecordBinding.rightBreastMilkET.getText().toString();
            if (leftAmount.equals("") || rightAmount.equals("")) {
                CustomAlertDialogs.showAlertDialog(this, "Alert", "Please Enter Correct data", this);
                return;
            }

        } else if (left) {
            leftAmount = activityCreateRecordBinding.leftBreastMilkET.getText().toString();
            if (leftAmount.equals("")) {
                CustomAlertDialogs.showAlertDialog(this, "Alert", "Please Enter Correct data", this);
                return;
            }
        } else if (right) {
            rightAmount = activityCreateRecordBinding.rightBreastMilkET.getText().toString();
            if (rightAmount.equals("")) {
                CustomAlertDialogs.showAlertDialog(this, "Alert", "Please Enter Correct data", this);
                return;
            }
        }

        ReusedFunctions.getInstance().HitJsonObjectWebAPI(this, true, ApiCodes.CREATE_RECORD, CallWebService.POST, API.BOTTLE_INFO, createJsonForPostRecord(), this);
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
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        CustomEditTextRegular customEditTextRegular = (CustomEditTextRegular) v;
        String quantity = customEditTextRegular.getText().toString();
        double otherBreastQuantity = 0;
        if (quantity.equals(""))
            quantity = "0";
        switch (v.getId()) {
            case R.id.totalMilkET:
                double totalQuantity = Double.parseDouble(quantity);
                String halfQuantity = String.format(Locale.getDefault(), "%.2f", totalQuantity / 2);
                activityCreateRecordBinding.leftBreastMilkET.setText(halfQuantity);
                activityCreateRecordBinding.rightBreastMilkET.setText(halfQuantity);
                break;
            case R.id.leftBreastMilkET:
                String rightBreastQuantity = activityCreateRecordBinding.rightBreastMilkET.getText().toString();
                if (!rightBreastQuantity.equals("")) {
                    otherBreastQuantity = Double.parseDouble(rightBreastQuantity);
                }
                onSingleBreastMilkChange(quantity, otherBreastQuantity);
                break;
            case R.id.rightBreastMilkET:
                String leftBreastQuantity = activityCreateRecordBinding.leftBreastMilkET.getText().toString();
                if (!leftBreastQuantity.equals("")) {
                    otherBreastQuantity = Double.parseDouble(leftBreastQuantity);
                }
                onSingleBreastMilkChange(quantity, otherBreastQuantity);
                break;

        }
        return false;
    }

    private void onSingleBreastMilkChange(String quantity, double otherBreastQuantity) {
        double totalQuantity = otherBreastQuantity + Double.parseDouble(quantity);
        String totalQuantityString = String.format(Locale.getDefault(), "%.2f", totalQuantity);
        activityCreateRecordBinding.totalMilkET.setText(totalQuantityString);
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
            jsonObject.put(Constants.LEFT_AMOUNT, leftAmount);
            jsonObject.put(Constants.RIGHT_AMOUNT, rightAmount);
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
