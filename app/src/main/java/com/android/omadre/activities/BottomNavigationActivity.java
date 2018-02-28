package com.android.omadre.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.omadre.R;
import com.android.omadre.fragments.HomeFragment;
import com.android.omadre.fragments.NotificationFragment;
import com.android.omadre.fragments.UserProfileFragment;
import com.androidlib.GlobalClasses.BaseActivity;
import com.androidlib.Utils.Utils;

public class BottomNavigationActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Utils.configureToolbarWithOutBackButton(this, toolbar, "Omadre");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        updateFragment(new HomeFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    updateFragment(new HomeFragment());
                    return true;
                case R.id.navigation_dashboard:
                    updateFragment(new NotificationFragment());
                    return true;
                case R.id.navigation_profile:
                    updateFragment(new UserProfileFragment());
                    return true;
            }
            return false;
        }
    };

    public void updateFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!(fragment instanceof HomeFragment))
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, "");
        fragmentTransaction.commit();
    }
}
