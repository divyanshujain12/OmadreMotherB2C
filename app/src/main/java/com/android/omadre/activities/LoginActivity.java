package com.android.omadre.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.android.omadre.Constants.API;
import com.android.omadre.Constants.ApiCodes;
import com.android.omadre.Constants.Constants;
import com.android.omadre.Models.MotherModel;
import com.android.omadre.R;
import com.android.omadre.Utils.MySharedPereference;
import com.android.omadre.Utils.ReusedFunctions;
import com.android.omadre.databinding.ActivityLoginBinding;
import com.androidlib.CustomViews.CustomToasts;
import com.androidlib.GlobalClasses.BaseActivity;
import com.androidlib.Models.ValidationModel;
import com.androidlib.Utils.CallWebService;
import com.androidlib.Utils.UniversalParser;
import com.androidlib.Utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding activityLoginBinding;
    private Validation validation;
    private HashMap<View, String> valuesHashMap = null;
    MotherModel motherModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        activityLoginBinding.setPresenter(this);
        initialize();
    }

    private void initialize() {
        validation = new Validation();
        validation.addValidationField(new ValidationModel(activityLoginBinding.usernameET, Validation.TYPE_NAME_VALIDATION, getString(R.string.username_error)));
        validation.addValidationField(new ValidationModel(activityLoginBinding.usernameET, Validation.TYPE_EMAIL_VALIDATION, getString(R.string.invalid_email_id)));
        validation.addValidationField(new ValidationModel(activityLoginBinding.passwordET, Validation.TYPE_PASSWORD_VALIDATION, getString(R.string.password_error_msg)));
    }

    public void onLoginClick() {
        //moveToNextScreen();
        valuesHashMap = validation.validate(this);
        if (valuesHashMap != null) {
            MySharedPereference.getInstance().setString(this, Constants.USER_NAME, valuesHashMap.get(activityLoginBinding.usernameET));
            MySharedPereference.getInstance().setString(this, Constants.PASSWORD, valuesHashMap.get(activityLoginBinding.passwordET));

            ReusedFunctions.getInstance().HitJsonObjectWebAPI(this, true, ApiCodes.MOTHER_LOG_IN, CallWebService.POST, API.MOTHERS_LOGIN, null, this);
        }
        //startActivity(new Intent(this, BottomNavigationActivity.class));
    }

    public void onSignupClick() {
        startActivity(new Intent(this, SignupActivity.class));
    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);
        CustomToasts.getInstance(this).showSuccessToast("login successful");
        motherModel = UniversalParser.getInstance().parseJsonObject(response.getJSONObject("Mother"), MotherModel.class);
        MySharedPereference.getInstance().setString(this, Constants.MOTHER_ID, String.valueOf(motherModel.getId()));
        MySharedPereference.getInstance().setBoolean(this, Constants.IS_LOGGED_IN, true);
        //startActivity(new Intent(this, BottomNavigationActivity.class));
        moveToNextScreen();

    }

    private void moveToNextScreen() {
        startActivity(new Intent(this, BottomNavigationActivity.class));
        finish();
    }

    @Override
    public void onFailure(String str, int apiType) {
        super.onFailure(str, apiType);
        CustomToasts.getInstance(this).showErrorToast(str);
    }
}
