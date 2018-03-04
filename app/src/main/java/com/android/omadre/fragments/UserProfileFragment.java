package com.android.omadre.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.omadre.Constants.API;
import com.android.omadre.Constants.ApiCodes;
import com.android.omadre.Constants.Constants;
import com.android.omadre.Models.MotherModel;
import com.android.omadre.R;
import com.android.omadre.Utils.MySharedPereference;
import com.android.omadre.Utils.ReusedFunctions;
import com.android.omadre.activities.BottomNavigationActivity;
import com.android.omadre.databinding.FragmentProfileBinding;
import com.androidlib.GlobalClasses.BaseFragment;
import com.androidlib.Utils.CallWebService;
import com.androidlib.Utils.UniversalParser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by divyanshuPC on 2/25/2018.
 */

public class UserProfileFragment extends BaseFragment {
    FragmentProfileBinding fragmentProfileBinding;
    MotherModel motherModel = null;
    private String motherId="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        fragmentProfileBinding.setPresenter(this);
        return fragmentProfileBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initViews();
        }
    }

    private void initViews() {
        motherId = MySharedPereference.getInstance().getString(getContext(),Constants.MOTHER_ID);
        if (motherModel == null)
            ReusedFunctions.getInstance().HitJsonObjectWebAPI(getContext(), true, ApiCodes.MOTHER_DETAIL, CallWebService.GET, String.format(API.MOTHER_DETAIL,motherId), null, this);
    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);
        motherModel = UniversalParser.getInstance().parseJsonObject(response.getJSONObject("Mother"), MotherModel.class);
        fragmentProfileBinding.setData(motherModel);
    }

    @Override
    public void onFailure(String str, int apiType) {
        super.onFailure(str, apiType);
    }
}
