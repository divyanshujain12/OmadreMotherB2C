package com.android.omadre.GlobalClasses;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.omadre.R;
import com.androidlib.GlobalClasses.BaseActivity;

import com.android.omadre.fragments.HomeFragment;


/**
 * Created by divyanshu.jain on 9/13/2017.
 */

public class BaseNavigationActivity extends BaseActivity implements View.OnClickListener {

    DrawerLayout drawer;
    Toolbar toolbar;
    Fragment initialHomeFragment;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawer = drawerLayout;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            boolean popped = false;
            if (count > 0)
                popped = getSupportFragmentManager().popBackStackImmediate();
            else
                super.onBackPressed();
        }
    }


    protected void setUpDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    public void setToolbar(String title, boolean otherThanHome) {
        toolbar.setTitle(title);
        if (otherThanHome) {
            toolbar.setNavigationIcon(R.drawable.ic_back);
        } else
            toolbar.setNavigationIcon(R.drawable.ic_menu);
    }

    public void setInitialHomeFragment(Fragment initialHomeFragment) {
        this.initialHomeFragment = initialHomeFragment;
    }

    public void updateFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!(fragment instanceof HomeFragment))
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, "");
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (fragment == initialHomeFragment)
            drawer.openDrawer(GravityCompat.START);
        else
            onBackPressed();
    }
}

