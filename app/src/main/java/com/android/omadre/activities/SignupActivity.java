package com.android.omadre.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;

import com.android.omadre.Constants.API;
import com.android.omadre.Constants.ApiCodes;
import com.android.omadre.Constants.Constants;
import com.android.omadre.R;
import com.android.omadre.Utils.ReusedFunctions;
import com.android.omadre.databinding.ActivitySignupBinding;
import com.androidlib.CustomViews.CustomAlertDialogs;
import com.androidlib.CustomViews.CustomToasts;
import com.androidlib.GlobalClasses.BaseActivity;
import com.androidlib.Interfaces.SnackBarCallback;
import com.androidlib.Models.ValidationModel;
import com.androidlib.Utils.CallWebService;
import com.androidlib.Utils.Utils;
import com.androidlib.Utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SignupActivity extends BaseActivity {
    private ActivitySignupBinding activitySignupBinding;
    private Validation validation;
    private boolean emailCard = true;
    private HashMap<View, String> hashMap;
    private ValidationModel emailNumberValidationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        activitySignupBinding.setPresenter(this);
        initViews();
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Utils.configureToolbarWithBackButton(this, toolbar, "SIGN UP");
        validation = new Validation();
        activitySignupBinding.emailOrNumberET.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        activitySignupBinding.emailOrNumberET.setHint(getResources().getString(R.string.email));
        emailNumberValidationModel = new ValidationModel(activitySignupBinding.emailOrNumberET, Validation.TYPE_EMAIL_VALIDATION, getString(R.string.email_error_msg));
        validation.addValidationField(new ValidationModel(activitySignupBinding.passwordET, Validation.TYPE_PASSWORD_VALIDATION, getString(R.string.password_error_msg)));
        validation.addValidationField(new ValidationModel(activitySignupBinding.dateET, Validation.TYPE_EMPTY_FIELD_VALIDATION, getString(R.string.date_error_msg)));
        validation.addValidationField(new ValidationModel(activitySignupBinding.monthET, Validation.TYPE_EMPTY_FIELD_VALIDATION, getString(R.string.month_error_msg)));
        validation.addValidationField(new ValidationModel(activitySignupBinding.yearET, Validation.TYPE_EMPTY_FIELD_VALIDATION, getString(R.string.year_error_msg)));
    }

    public void onEmailClick() {
        changeButtonColor(true);
        activitySignupBinding.emailOrNumberET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        activitySignupBinding.emailOrNumberET.setHint(getResources().getString(R.string.email));

    }

    public void onPhoneNumberClick() {
        changeButtonColor(false);
        activitySignupBinding.emailOrNumberET.setInputType(InputType.TYPE_CLASS_PHONE);
        activitySignupBinding.emailOrNumberET.setHint(getResources().getString(R.string.phone_number));

    }

    public void onSubmitClick() {
        validation.removeValidationField(emailNumberValidationModel);
        if (this.emailCard) {
            emailNumberValidationModel = new ValidationModel(activitySignupBinding.emailOrNumberET, Validation.TYPE_EMAIL_VALIDATION, getString(R.string.email_error_msg));
            validation.addValidationField(emailNumberValidationModel);
        } else {
            emailNumberValidationModel = new ValidationModel(activitySignupBinding.emailOrNumberET, Validation.TYPE_PHONE_VALIDATION, getString(R.string.phone_number_err_msg));
            validation.addValidationField(emailNumberValidationModel);
        }
        hashMap = validation.validate(this);
        if (hashMap != null) {
            //createJsonForSignup();
            ReusedFunctions.getInstance().HitJsonObjectWebAPI(this, true, ApiCodes.MOTHER_SIGN_UP, CallWebService.POST, API.MOTHERS, createJsonForSignup(), this);
        }
        //startActivity(new Intent(this, BottomNavigationActivity.class));
    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);
        CustomAlertDialogs.showAlertDialog(this, "Congratulations", "Account Created Successfully !", new SnackBarCallback() {
            @Override
            public void doAction() {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

    }

    @Override
    public void onFailure(String str, int apiType) {
        super.onFailure(str, apiType);
        CustomToasts.getInstance(this).showErrorToast(str);
    }

    private JSONObject createJsonForSignup() {
        String time = hashMap.get(activitySignupBinding.yearET) + "-" + hashMap.get(activitySignupBinding.monthET) + "-" + hashMap.get(activitySignupBinding.dateET);
        long timeInMS = Utils.getInstance().getTimeInMS(time, Utils.DATE_FORMAT);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.USER_NAME, hashMap.get(activitySignupBinding.emailOrNumberET));
            jsonObject.put(Constants.PASSWORD, hashMap.get(activitySignupBinding.passwordET));
            jsonObject.put(Constants.DOB, timeInMS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    private void changeButtonColor(boolean emailCard) {
        this.emailCard = emailCard;
        if (emailCard) {
            activitySignupBinding.emailCV.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
            activitySignupBinding.phoneCV.setCardBackgroundColor(getResources().getColor(R.color.secondary_color));
        } else {
            activitySignupBinding.phoneCV.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
            activitySignupBinding.emailCV.setCardBackgroundColor(getResources().getColor(R.color.secondary_color));
        }
    }
}
