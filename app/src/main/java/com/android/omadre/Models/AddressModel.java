package com.android.omadre.Models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by divyanshu.jain on 9/29/2017.
 */

public class AddressModel extends BaseObservable {
    int id;
    String address;
    String city;
    String state;
    int pincode;
    LocationModel location;
@Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Bindable
    public String getAddress() {
        return address;
    }
    @Bindable
    public void setAddress(String address) {
        this.address = address;
    }
    @Bindable
    public String getCity() {
        return city;
    }
    @Bindable
    public void setCity(String city) {
        this.city = city;
    }
    @Bindable
    public String getState() {
        return state;
    }
    @Bindable
    public void setState(String state) {
        this.state = state;
    }
    @Bindable
    public int getPincode() {
        return pincode;
    }
    @Bindable
    public void setPincode(int pincode) {
        this.pincode = pincode;
    }
    @Bindable
    public LocationModel getLocation() {
        return location;
    }
    @Bindable
    public void setLocation(LocationModel location) {
        this.location = location;
    }
}
