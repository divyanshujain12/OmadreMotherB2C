package com.android.omadre.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.android.omadre.R;
import com.android.omadre.databinding.ActivityCreateRecordBinding;
import com.androidlib.GlobalClasses.BaseActivity;

public class CreateRecordActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    private ActivityCreateRecordBinding activityCreateRecordBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityCreateRecordBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_record);
        activityCreateRecordBinding.setPresenter(this);
    }

    public void onBarcodeClick(){

    }
    public void onSubmitClick(){

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
}
