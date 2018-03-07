package com.android.omadre.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.android.omadre.Constants.Constants;
import com.android.omadre.R;
import com.android.omadre.Utils.MySharedPereference;
import com.android.omadre.databinding.ActivityOrderBarcodeBinding;
import com.androidlib.CustomViews.CustomToasts;
import com.androidlib.GlobalClasses.BaseActivity;
import com.androidlib.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class OrderBarcodeActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    private ActivityOrderBarcodeBinding activityOrderBarcodeBinding;
    private String[] quantityArray = {"2", "3", "4", "5"};
    private String selectedQuantity;
    private String type;
    private String[] trialOfferPrice = {"2", "4", "8"};
    private String[] monthlyOfferPrice = {"5", "15", "20"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_barcode);
        activityOrderBarcodeBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_barcode);
        activityOrderBarcodeBinding.setPresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Utils.configureToolbarWithBackButton(this, toolbar, "Order Barcode");

        initViews();
    }

    private void initViews() {
        activityOrderBarcodeBinding.milkPumpSP.setOnItemSelectedListener(this);
    }

    public void onTrialClick() {
        enableButtons();
        changePaymentType(true);
    }

    public void onMonthlyClick() {
        enableButtons();
        changePaymentType(false);
    }

    public void onSwipeToPayClick() {
        Intent intent = new Intent(this, DeliverActivity.class);
        intent.putExtra(Constants.DATA, createJsonForMembership().toString());
        startActivity(intent);
        //startActivity(new Intent(this, DeliverActivity.class));
    }

    public void onPayOneTimeClick() {
       // ReusedFunctions.getInstance().HitJsonObjectWebAPI(this, true, ApiCodes.MEMBERSHIP, CallWebService.POST, API.MEMBERSHIP, createJsonForMembership(), this);
    }


    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);

        CustomToasts.getInstance(this).showSuccessToast("Now you are a member of Omadre");
        finish();
    }

    private void changePaymentType(boolean trialClick) {
        if (trialClick) {
            type = getString(R.string.trial);
            activityOrderBarcodeBinding.trialFL.setBackgroundResource(R.drawable.rounded_button_primary_bg);
            activityOrderBarcodeBinding.monthlyFL.setBackgroundResource(R.drawable.rounded_button_secondary_bg);
        } else {
            type = getString(R.string.monthly);
            activityOrderBarcodeBinding.monthlyFL.setBackgroundResource(R.drawable.rounded_button_primary_bg);
            activityOrderBarcodeBinding.trialFL.setBackgroundResource(R.drawable.rounded_button_secondary_bg);
        }
    }

    private void enableButtons() {
        activityOrderBarcodeBinding.payOneTimeBT.setEnabled(true);
        activityOrderBarcodeBinding.swipeToPayBT.setEnabled(true);
        activityOrderBarcodeBinding.payOneTimeBT.setBackgroundResource(R.drawable.rounded_button_primary_bg);
        activityOrderBarcodeBinding.swipeToPayBT.setBackgroundResource(R.drawable.rounded_button_primary_bg);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedQuantity = quantityArray[position];
        activityOrderBarcodeBinding.trialTV.setText(String.format(getString(R.string.trial_offer_n_5), trialOfferPrice[position]));
        activityOrderBarcodeBinding.monthlyTV.setText(String.format(getString(R.string.monthly_offer_n_15), monthlyOfferPrice[position]));
        //CustomToasts.getInstance(this).showSuccessToast(selectedQuantity);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private JSONObject createJsonForMembership() {
        String currentDate = Utils.getCurrentTime(Utils.DATE_FORMAT);
        String nextMonthDate = nextOneMonthData();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.TYPE, type);
            jsonObject.put(Constants.MOTHERID, MySharedPereference.getInstance().getString(this, Constants.MOTHER_ID));
            jsonObject.put(Constants.START_DATE, currentDate);
            jsonObject.put(Constants.END_DATE, nextMonthDate);
            jsonObject.put(Constants.BARCODE_PER_DAY, selectedQuantity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private String nextOneMonthData() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        return Utils.formatDateAndTime(calendar.getTime().getTime(), Utils.DATE_FORMAT);
    }
}
