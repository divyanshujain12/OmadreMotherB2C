package com.android.omadre.activities;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.android.omadre.R;
import com.android.omadre.databinding.ActivityFeedBinding;
import com.androidlib.CustomViews.CustomAlertDialogs;
import com.androidlib.CustomViews.CustomToasts;
import com.androidlib.Fragments.RuntimePermissionHeadlessFragment;
import com.androidlib.GlobalClasses.BaseActivity;
import com.androidlib.Interfaces.SnackBarCallback;
import com.androidlib.Utils.CommonFunctions;
import com.androidlib.Utils.Utils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class FeedActivity extends BaseActivity implements RuntimePermissionHeadlessFragment.PermissionCallback {

    RuntimePermissionHeadlessFragment runtimePermissionHeadlessFragment;
    private String[] permissions;
    ActivityFeedBinding activityFeedBinding;
    private String qrCodeData;
    private boolean preparing = false;


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

    }

    public void onPrepareClick() {
        if (!preparing) {
            activityFeedBinding.prepareBT.setBackgroundResource(R.drawable.rounded_button_secondary_bg);
            activityFeedBinding.prepareBT.setText(R.string.preparing);
            preparing = true;
        } else {
            activityFeedBinding.prepareBT.setBackgroundResource(R.drawable.rounded_button_primary_bg);
            activityFeedBinding.prepareBT.setText(R.string.prepared);
            activityFeedBinding.prepareBT.setEnabled(false);
            activityFeedBinding.startFeedBT.setEnabled(true);
            activityFeedBinding.startFeedBT.setBackgroundResource(R.drawable.rounded_button_primary_bg);
        }
    }

    public void onStoreClick() {

    }

    public void onStartFeedClick() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                CustomToasts.getInstance(this).showErrorToast("Result Not Found");
            } else {
                qrCodeData = result.getContents();
                activityFeedBinding.prepareBT.setEnabled(true);
                activityFeedBinding.prepareBT.setBackgroundResource(R.drawable.rounded_button_primary_bg);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (runtimePermissionHeadlessFragment == null) {
            runtimePermissionHeadlessFragment = CommonFunctions.getInstance().addRuntimePermissionFragment(this, this);
            ;

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
}
