package com.android.omadre.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.omadre.Constants.Constants;
import com.android.omadre.R;
import com.androidlib.CustomFontViews.CustomEditTextRegular;
import com.androidlib.Utils.CallWebService;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by divyanshu.jain on 9/25/2017.
 */

public class ReusedFunctions {
    private static final ReusedFunctions ourInstance = new ReusedFunctions();
    private AlertDialog alertDialog;

    public static ReusedFunctions getInstance() {
        return ourInstance;
    }


    private ReusedFunctions() {
    }


    public void HitJsonObjectWebAPI(Context context, boolean showProgressBar, int apiCode, int requestType, String url, JSONObject jsonObject, CallWebService.ObjectResponseCallBack responseCallback) {
        CallWebService callWebService = CallWebService.getInstance(context, showProgressBar, apiCode);
        callWebService.setHeadersValue(MySharedPereference.getInstance().getString(context, Constants.USER_NAME), MySharedPereference.getInstance().getString(context, Constants.PASSWORD), "");
        CallWebService.getInstance(context, showProgressBar, apiCode).hitJsonObjectRequestAPI(requestType, url, jsonObject, responseCallback);
    }

    public void HitJsonArrayWebAPI(Context context, boolean showProgressBar, int apiCode, int requestType, String url, JSONArray jsonArray, CallWebService.ArrayResponseCallback responseCallback) {
        CallWebService callWebService = CallWebService.getInstance(context, showProgressBar, apiCode);

        callWebService.setHeadersValue(MySharedPereference.getInstance().getString(context, Constants.USER_NAME), MySharedPereference.getInstance().getString(context, Constants.USER_NAME), "");

        callWebService.setHeadersValue(MySharedPereference.getInstance().getString(context, Constants.USER_NAME), MySharedPereference.getInstance().getString(context, Constants.PASSWORD), "");

        CallWebService.getInstance(context, showProgressBar, apiCode).hitJsonArrayRequestAPI(requestType, url, jsonArray, responseCallback);
    }

    public void showMilkQuantityAlert(Context context, final FeedingAmountInterface feedingAmountInterface) {

        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.milk_quantity_alert, null);
        setupFullWidthDialog();
        final CustomEditTextRegular milkQuantityET = (CustomEditTextRegular) layout.findViewById(R.id.milkQuantityET);

        layout.findViewById(R.id.milkQuantitySubmitBT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedingQuantity = milkQuantityET.getText().toString();
                feedingAmountInterface.FeedingAmount(feedingQuantity);
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(layout);
        alertDialog.show();
    }

    private void setupFullWidthDialog() {
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

    }

    public String getNotificationSampleJSON() {
        String sampleJson = "[\n" +
                "{\n" +
                "\"title\":\"Milk Expiry\",\n" +
                "\"description\":\"the milk kept in refrigerator is about to expire\",\n" +
                "\"date\":\"23rd February 2018\"\n" +
                "},\n" +
                "{\n" +
                "\"title\":\"Milk Expiry\",\n" +
                "\"description\":\"the milk kept in refrigerator is about to expire\",\n" +
                "\"date\":\"23rd February 2018\"\n" +
                "},\n" +
                "{\n" +
                "\"title\":\"Milk Expiry\",\n" +
                "\"description\":\"the milk kept in refrigerator is about to expire\",\n" +
                "\"date\":\"23rd February 2018\"\n" +
                "},\n" +
                "{\n" +
                "\"title\":\"Milk Expiry\",\n" +
                "\"description\":\"the milk kept in refrigerator is about to expire\",\n" +
                "\"date\":\"23rd February 2018\"\n" +
                "},\n" +
                "{\n" +
                "\"title\":\"Milk Expiry\",\n" +
                "\"description\":\"the milk kept in refrigerator is about to expire\",\n" +
                "\"date\":\"23rd February 2018\"\n" +
                "},\n" +
                "{\n" +
                "\"title\":\"Milk Expiry\",\n" +
                "\"description\":\"the milk kept in refrigerator is about to expire\",\n" +
                "\"date\":\"23rd February 2018\"\n" +
                "},\n" +
                "{\n" +
                "\"title\":\"Milk Expiry\",\n" +
                "\"description\":\"the milk kept in refrigerator is about to expire\",\n" +
                "\"date\":\"23rd February 2018\"\n" +
                "},\n" +
                "{\n" +
                "\"title\":\"Milk Expiry\",\n" +
                "\"description\":\"the milk kept in refrigerator is about to expire\",\n" +
                "\"date\":\"23rd February 2018\"\n" +
                "},\n" +
                "{\n" +
                "\"title\":\"Milk Expiry\",\n" +
                "\"description\":\"the milk kept in refrigerator is about to expire\",\n" +
                "\"date\":\"23rd February 2018\"\n" +
                "},\n" +
                "{\n" +
                "\"title\":\"Milk Expiry\",\n" +
                "\"description\":\"the milk kept in refrigerator is about to expire\",\n" +
                "\"date\":\"23rd February 2018\"\n" +
                "},\n" +
                "{\n" +
                "\"title\":\"Milk Expiry\",\n" +
                "\"description\":\"the milk kept in refrigerator is about to expire\",\n" +
                "\"date\":\"23rd February 2018\"\n" +
                "}\n" +
                "]";

        return sampleJson;
    }

}


