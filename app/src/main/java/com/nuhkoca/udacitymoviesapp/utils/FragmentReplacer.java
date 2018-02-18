package com.nuhkoca.udacitymoviesapp.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.nuhkoca.udacitymoviesapp.R;

import timber.log.Timber;

/**
 * Created by nuhkoca on 2/17/18.
 */

public class FragmentReplacer {
    private static FragmentManager mFragmentManager;

    private Context context;

    public static FragmentReplacer create(Context context) {
        FragmentReplacer instance = new FragmentReplacer();
        instance.context = context;

        return instance;
    }

    public void replaceFragment(AppCompatActivity instance, int holderId, Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        mFragmentManager = instance.getSupportFragmentManager();
        boolean fragmentPopped = mFragmentManager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && mFragmentManager.findFragmentByTag(backStateName) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.add(holderId, fragment, backStateName);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
        } else {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.replace(holderId, fragment, backStateName);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }

        toastSuccess();

    }

    public int getBackStackEntryCount() {
        return mFragmentManager.getBackStackEntryCount();
    }

    private void toastSuccess() {
        if (getBackStackEntryCount() > -1) {
            Timber.d(context.getString(R.string.fragment_loading_successful));
        }
    }
}