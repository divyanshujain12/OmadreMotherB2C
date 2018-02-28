package com.android.omadre.Models;

import android.databinding.BaseObservable;

/**
 * Created by divyanshuPC on 2/25/2018.
 */

public class NotificationModel extends BaseObservable{


    String title;
    String description;
    String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
