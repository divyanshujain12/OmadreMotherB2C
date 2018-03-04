package com.android.omadre.Models;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

public class OnlyMotherModel extends BaseObservable implements Parcelable {
    String id;
    String username;
    String password;
    String name;
    String phone;
    int addressId;
    String uniqueCode;

    public OnlyMotherModel() {
    }

    protected OnlyMotherModel(Parcel in) {
        id = in.readString();
        username = in.readString();
        password = in.readString();
        name = in.readString();
        phone = in.readString();
        addressId = in.readInt();
        uniqueCode = in.readString();
    }

    public static final Creator<OnlyMotherModel> CREATOR = new Creator<OnlyMotherModel>() {
        @Override
        public OnlyMotherModel createFromParcel(Parcel in) {
            return new OnlyMotherModel(in);
        }

        @Override
        public OnlyMotherModel[] newArray(int size) {
            return new OnlyMotherModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeInt(addressId);
        dest.writeString(uniqueCode);
    }
}