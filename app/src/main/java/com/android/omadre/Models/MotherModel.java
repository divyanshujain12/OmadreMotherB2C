package com.android.omadre.Models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by divyanshu.jain on 9/29/2017.
 */

public class MotherModel extends BaseObservable implements Parcelable {
    int id;
    String username;
    String name;
    ArrayList<String> roles;
    int addressId;
    AddressModel Address;
    ArrayList<PatientsModel> Patients;
public MotherModel(){}
    protected MotherModel(Parcel in) {
        id = in.readInt();
        username = in.readString();
        name = in.readString();
        roles = in.createStringArrayList();
        addressId = in.readInt();
        Patients = in.createTypedArrayList(PatientsModel.CREATOR);
    }

    public static final Creator<MotherModel> CREATOR = new Creator<MotherModel>() {
        @Override
        public MotherModel createFromParcel(Parcel in) {
            return new MotherModel(in);
        }

        @Override
        public MotherModel[] newArray(int size) {
            return new MotherModel[size];
        }
    };

    @Bindable
    public int getId() {
        return id;
    }
    @Bindable
    public void setId(int id) {
        this.id = id;
    }
    @Bindable
    public String getUsername() {
        return username;
    }
    @Bindable
    public void setUsername(String username) {
        this.username = username;
    }
    @Bindable
    public String getName() {
        return name;
    }
    @Bindable
    public void setName(String name) {
        this.name = name;
    }
    @Bindable
    public ArrayList<String> getRoles() {
        return roles;
    }
    @Bindable
    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }
    @Bindable
    public int getAddressId() {
        return addressId;
    }
    @Bindable
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
    @Bindable
    public AddressModel getAddressModel() {
        return Address;
    }
    @Bindable
    public void setAddressModel(AddressModel Address) {
        this.Address = Address;
    }

    public ArrayList<PatientsModel> getPatients() {
        return Patients;
    }

    public void setPatients(ArrayList<PatientsModel> patients) {
        Patients = patients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(name);
        dest.writeStringList(roles);
        dest.writeInt(addressId);
        dest.writeTypedList(Patients);
    }
}
