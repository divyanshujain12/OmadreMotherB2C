package com.android.omadre.GlobalClasses;

import android.util.Log;

import com.androidlib.GlobalClasses.LibInit;
//import com.example.divyanshu.smyt.com.android.omadre.Utils.LruBitmapCache;

/**
 * Created by divyanshu.jain on 9/15/2016.
 */
public class MyApp extends LibInit {
    private static final String TAG = MyApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();


    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
        Log.w(TAG, "Low Memory");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}