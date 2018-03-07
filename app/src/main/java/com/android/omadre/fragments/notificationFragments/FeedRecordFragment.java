package com.android.omadre.fragments.notificationFragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.omadre.Constants.API;
import com.android.omadre.Constants.ApiCodes;
import com.android.omadre.Constants.Constants;
import com.android.omadre.Models.BottleInfoModel;
import com.android.omadre.Models.FeedModel;
import com.android.omadre.R;
import com.android.omadre.Utils.MySharedPereference;
import com.android.omadre.Utils.ReusedFunctions;
import com.android.omadre.adapters.FeedRecordAdapter;
import com.android.omadre.databinding.FragmentFeedRecordBinding;
import com.androidlib.GlobalClasses.BaseFragment;
import com.androidlib.Utils.CallWebService;
import com.androidlib.Utils.UniversalParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by divyanshuPC on 3/7/2018.
 */

public class FeedRecordFragment extends BaseFragment {
    FragmentFeedRecordBinding fragmentFeedRecordBinding;
    FeedRecordAdapter feedRecordAdapter;
    ArrayList<FeedModel> feedModels;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentFeedRecordBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed_record, container, false);
        fragmentFeedRecordBinding.setPresenter(this);
        return fragmentFeedRecordBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews() {
        fragmentFeedRecordBinding.feedRecordRV.setLayoutManager(new LinearLayoutManager(getContext()));
        String motherId = MySharedPereference.getInstance().getString(getContext(), Constants.MOTHER_ID);
        ReusedFunctions.getInstance().HitJsonObjectWebAPI(getContext(), true, ApiCodes.FEED_RECORD_LISTING, CallWebService.GET, String.format(API.FEED_RECORD_LISTING, motherId), null, this);
    }

    @Override
    public void onJsonObjectSuccess(JSONObject response, int apiType) throws JSONException {
        super.onJsonObjectSuccess(response, apiType);
        feedModels = UniversalParser.getInstance().parseJsonArrayWithJsonObject(response.getJSONArray("Feeds"), FeedModel.class);
        feedRecordAdapter = new FeedRecordAdapter(getContext(), feedModels, this);
        fragmentFeedRecordBinding.feedRecordRV.setAdapter(feedRecordAdapter);
    }
}

