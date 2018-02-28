package com.android.omadre.Models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by divyanshu.jain on 9/29/2017.
 */

public class LocationModel extends BaseObservable {
    double lat;
    double lng;
    @Bindable
    public double getLat() {
        return lat;
    }
    @Bindable
    public void setLat(double lat) {
        this.lat = lat;
    }
    @Bindable
    public double getLng() {
        return lng;
    }
    @Bindable
    public void setLng(double lng) {
        this.lng = lng;
    }
}
