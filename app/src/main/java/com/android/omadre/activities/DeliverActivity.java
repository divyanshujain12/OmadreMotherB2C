package com.android.omadre.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.omadre.Constants.API;
import com.android.omadre.Constants.Constants;
import com.android.omadre.R;
import com.android.omadre.Utils.MySharedPereference;
import com.android.omadre.Utils.ReusedFunctions;
import com.android.omadre.databinding.ActivityDeliverBinding;
import com.androidlib.CustomViews.CustomToasts;
import com.androidlib.GlobalClasses.BaseActivity;
import com.androidlib.Models.ValidationModel;
import com.androidlib.Utils.CallWebService;
import com.androidlib.Utils.Utils;
import com.androidlib.Utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.android.omadre.Constants.ApiCodes.MEMBERSHIP;
import static com.android.omadre.Constants.ApiCodes.REQUEST_QR_CODE;


public class DeliverActivity extends BaseActivity {
    private String memberShipRequest;
    ActivityDeliverBinding activityDeliverBinding;
    private Validation validation;
    private HashMap<View, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver);
        activityDeliverBinding = DataBindingUtil.setContentView(this, R.layout.activity_deliver);
        activityDeliverBinding.setPresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Utils.configureToolbarWithBackButton(this, toolbar, "Deliver");

        initViews();
    }

    private void initViews() {
        validation = new Validation();
        validation.addValidationField(new ValidationModel(activityDeliverBinding.addressET, Validation.TYPE_NAME_VALIDATION, getString(R.string.address_err)));
        memberShipRequest = getIntent().getStringExtra(Constants.DATA);
    }

    public void onPayClick() {
        hashMap = validation.validate(this);
        if (hashMap != null) {
            try {
                ReusedFunctions.getInstance().HitJsonObjectWebAPI(this, true, MEMBERSHIP, CallWebService.POST, API.MEMBERSHIP, new JSONObject(memberShipRequest), this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);
        switch (apiType) {
            case MEMBERSHIP:
                hitOrderBarcodeApi();
                break;
            case REQUEST_QR_CODE:
                CustomToasts.getInstance(this).showSuccessToast("Barcode Ordered Successfully");
                goToHome();
                break;
        }
    }

    @Override
    public void onFailure(String str, int apiType) {
        super.onFailure(str, apiType);
        CustomToasts.getInstance(this).showErrorToast(str);

    }

    private void hitOrderBarcodeApi() {
        String motherId = MySharedPereference.getInstance().getString(this, Constants.MOTHER_ID);
        ReusedFunctions.getInstance().HitJsonObjectWebAPI(this, true, REQUEST_QR_CODE, CallWebService.GET, String.format(API.REQUEST_QR_CODE, motherId), null, this);
    }

    private void goToHome() {
        Intent intent = new Intent(this, BottomNavigationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
