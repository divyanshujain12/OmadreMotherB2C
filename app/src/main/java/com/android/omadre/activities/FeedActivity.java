package com.android.omadre.activities;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.omadre.Constants.API;
import com.android.omadre.Constants.ApiCodes;
import com.android.omadre.Constants.Constants;
import com.android.omadre.Models.BottleInfoModel;
import com.android.omadre.R;
import com.android.omadre.Utils.MySharedPereference;
import com.android.omadre.Utils.ReusedFunctions;
import com.android.omadre.databinding.ActivityFeedBinding;
import com.androidlib.CustomViews.CustomAlertDialogs;
import com.androidlib.CustomViews.CustomToasts;
import com.androidlib.Fragments.RuntimePermissionHeadlessFragment;
import com.androidlib.GlobalClasses.BaseActivity;
import com.androidlib.Interfaces.SnackBarCallback;
import com.androidlib.Utils.CallWebService;
import com.androidlib.Utils.CommonFunctions;
import com.androidlib.Utils.UniversalParser;
import com.androidlib.Utils.Utils;
import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class FeedActivity extends BaseActivity implements RuntimePermissionHeadlessFragment.PermissionCallback, ZXingScannerView.ResultHandler {

    RuntimePermissionHeadlessFragment runtimePermissionHeadlessFragment;
    private String[] permissions;
    ActivityFeedBinding activityFeedBinding;
    private String qrCodeData;
    private boolean preparing = false;
    private ArrayList<BottleInfoModel> bottleInfoModels;
    private HashMap<String, Integer> bottleMap = new HashMap<>();
    private HashMap<String, Integer> quantityMap = new HashMap<>();
    private ZXingScannerView zXingScannerView;
    private ArrayList<String> qrCodesList = new ArrayList<>();
    private int currentScanQrCodeNumber = 0;
    private double quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFeedBinding = DataBindingUtil.setContentView(this, R.layout.activity_feed);
        activityFeedBinding.setPresenter(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Utils.configureToolbarWithBackButton(this, toolbar, "FEED");
        initViews();
    }

    private void initViews() {
        permissions = new String[]{
                Manifest.permission.CAMERA
        };
    }

    public void onScanBarcodeClick() {
        String quantityInStr = activityFeedBinding.quantityET.getText().toString();
        if (!quantityInStr.equals("")) {
            quantity = Double.parseDouble(quantityInStr);
            String motherId = MySharedPereference.getInstance().getString(this, Constants.MOTHER_ID);
            ReusedFunctions.getInstance().HitJsonObjectWebAPI(this, true, ApiCodes.GET_REQUIRED_BOTTLES, CallWebService.GET, String.format(API.GET_REQUIRED_BOTTLES, motherId, quantityInStr), null, this);
        }
    }


    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);
        bottleMap = new HashMap<>();
        quantityMap = new HashMap<>();
        qrCodesList = new ArrayList<>();
        if (!response.has(Constants.STATUS)) {
            JSONArray bottlesArray = response.getJSONArray(Constants.REQUIRE_BOTTLES);
            bottleInfoModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(bottlesArray, BottleInfoModel.class);

            for (int i = 0; i < bottlesArray.length(); i++) {
                JSONObject bottleObject = bottlesArray.getJSONObject(i);
                String qrCodeId = bottleObject.getString(Constants.GENERATED_QR_CODE_ID);
                bottleMap.put(qrCodeId, bottleObject.getInt(Constants.PRIORITY));
                quantityMap.put(qrCodeId, bottleObject.getInt(Constants.CURRENTLY_USED));
            }
            openScanner();
        } else {
            CustomToasts.getInstance(this).showErrorToast(response.getString(Constants.STATUS));
        }
    }

    private void openScanner() {
        activityFeedBinding.quantityLL.setVisibility(View.GONE);
        activityFeedBinding.scannerViewFL.setVisibility(View.VISIBLE);
        zXingScannerView = new ZXingScannerView(this);
        zXingScannerView.setResultHandler(this);
        activityFeedBinding.scannerFL.removeAllViews();
        activityFeedBinding.scannerFL.addView(zXingScannerView);

        zXingScannerView.startCamera();
    }

    @Override
    public void onFailure(String str, int apiType) {
        super.onFailure(str, apiType);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (runtimePermissionHeadlessFragment == null) {
            runtimePermissionHeadlessFragment = CommonFunctions.getInstance().addRuntimePermissionFragment(this, this);
        }
    }

    @Override
    public void onPermissionGranted(int i) {
        if (i == 1) {

        }
    }

    @Override
    public void onPermissionDenied(int i) {
        CustomAlertDialogs.showAlertDialog(this, "Alert", "You have to grant camera permission to use this functionality", new SnackBarCallback() {
            @Override
            public void doAction() {
                finish();
            }
        });
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof RuntimePermissionHeadlessFragment) {
            runtimePermissionHeadlessFragment.addAndCheckPermission(permissions, 1);
        }

    }

    @Override
    public void handleResult(Result result) {
        CustomToasts.getInstance(this).showSuccessToast(result.getText());

        String scannedQrCode = result.getText();
        if (bottleMap.containsKey(scannedQrCode)) {
            int priority = bottleMap.get(scannedQrCode);
            if (qrCodesList.size() >= priority - 1) {
                qrCodesList.add(scannedQrCode);
                if (checkQuantity())
                    handleQrCodeResult(0, scannedQrCode);
                else
                    handleQrCodeResult(2, scannedQrCode);
            } else {
                handleQrCodeResult(1, scannedQrCode);
            }
        } else {
            handleQrCodeResult(1, scannedQrCode);
        }
        zXingScannerView.resumeCameraPreview(this);
    }

    private void handleQrCodeResult(int type, String scannedQrCode) {
        switch (type) {
            case 0:
                zXingScannerView.stopCamera();
                activityFeedBinding.backgroundFL.setBackgroundResource(R.drawable.green_border_transparent_bg);
                activityFeedBinding.scanResultTV.setTextColor(getResources().getColor(R.color.green));
                activityFeedBinding.scanResultTV.setText(getString(R.string.green_barcode));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moveToNextActivity();
                    }
                }, 2000);

                break;
            case 1:
                activityFeedBinding.backgroundFL.setBackgroundResource(R.drawable.red_border_transparent_bg);
                activityFeedBinding.scanResultTV.setTextColor(getResources().getColor(R.color.red));
                activityFeedBinding.scanResultTV.setText(getString(R.string.red_barcode));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetBackground();
                    }
                }, 2000);
                break;
            case 2:
                activityFeedBinding.backgroundFL.setBackgroundResource(R.drawable.yellow_border_transparent_bg);
                activityFeedBinding.scanResultTV.setTextColor(getResources().getColor(R.color.yellow));
                activityFeedBinding.scanResultTV.setText(getString(R.string.yellow_barcode));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetBackground();
                    }
                }, 2000);
                break;
        }
    }


    private void moveToNextActivity() {
        activityFeedBinding.quantityLL.setVisibility(View.VISIBLE);
        activityFeedBinding.scannerViewFL.setVisibility(View.GONE);

        startActivity(new Intent(this, RecordFeedActivity.class));

    }

    private void resetBackground() {
        activityFeedBinding.backgroundFL.setBackgroundColor(getColor(android.R.color.transparent));
        activityFeedBinding.scanResultTV.setTextColor(getResources().getColor(R.color.black));
        activityFeedBinding.scanResultTV.setText("");
    }

    private boolean checkQuantity() {

        double scannedQrcodeQuantity = 0;
        for (int i = 0; i < qrCodesList.size(); i++) {
            String qrCode = qrCodesList.get(i);
            scannedQrcodeQuantity += quantityMap.get(qrCode);
        }
        if (scannedQrcodeQuantity >= quantity)
            return true;
        else

            return false;

    }

    final Handler handler = new Handler();

}
