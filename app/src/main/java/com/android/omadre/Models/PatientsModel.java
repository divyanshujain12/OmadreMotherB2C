package com.android.omadre.Models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.androidlib.Utils.ImageLoading;

/**
 * Created by divyanshu.jain on 10/3/2017.
 */

public class PatientsModel extends BaseObservable implements Parcelable {
    String id;
    String name;
    int age;
    int bedNumber;
    String feedingType;
    String bloodGroup;
    String QRCode;
    String notes;
    String motherId;
    String dob;
    MotherModel Mother;

    public PatientsModel() {
    }


    protected PatientsModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        age = in.readInt();
        bedNumber = in.readInt();
        feedingType = in.readString();
        bloodGroup = in.readString();
        QRCode = in.readString();
        notes = in.readString();
        motherId = in.readString();
        dob = in.readString();
        Mother = in.readParcelable(MotherModel.class.getClassLoader());
    }

    public static final Creator<PatientsModel> CREATOR = new Creator<PatientsModel>() {
        @Override
        public PatientsModel createFromParcel(Parcel in) {
            return new PatientsModel(in);
        }

        @Override
        public PatientsModel[] newArray(int size) {
            return new PatientsModel[size];
        }
    };

    @Bindable
    public String getId() {
        return id;
    }

    @Bindable
    public void setId(String id) {
        this.id = id;
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
    public int getAge() {
        return age;
    }

    @Bindable
    public void setAge(int age) {
        this.age = age;
    }

    @Bindable
    public int getBedNumber() {
        return bedNumber;
    }

    @Bindable
    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    @Bindable
    public String getFeedingType() {
        return feedingType;
    }

    @Bindable
    public void setFeedingType(String feedingType) {
        this.feedingType = feedingType;
    }

    @Bindable
    public String getBloodGroup() {
        return bloodGroup;
    }

    @Bindable
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    @Bindable
    public String getQRCode() {
        return QRCode;
    }

    @Bindable
    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    @Bindable
    public String getNotes() {
        return notes;
    }

    @Bindable
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Bindable
    public String getMotherId() {
        return motherId;
    }

    @Bindable
    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    @Bindable
    public MotherModel getMother() {
        return Mother;
    }

    @Bindable
    public void setMother(MotherModel mother) {
        Mother = mother;

    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }


    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        ImageLoading imageLoading = new ImageLoading(view.getContext());
        imageLoading.LoadImage(imageUrl, view, null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeInt(age);
        parcel.writeInt(bedNumber);
        parcel.writeString(feedingType);
        parcel.writeString(bloodGroup);
        parcel.writeString(QRCode);
        parcel.writeString(notes);
        parcel.writeString(motherId);
        parcel.writeString(dob);
        parcel.writeParcelable(Mother, i);
    }
}
