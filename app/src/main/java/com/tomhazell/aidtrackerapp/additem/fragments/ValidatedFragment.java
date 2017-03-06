package com.tomhazell.aidtrackerapp.additem.fragments;

import android.support.v4.app.Fragment;

import com.tomhazell.aidtrackerapp.additem.NewItem;

/**
 * This is an interface that a fragment should implement to be used with the AddItemActivity
 */

public interface ValidatedFragment {

    boolean validateDetails();

    Fragment getFragment();

    NewItem addDataToModel(NewItem newItem);

    String getTitle();
}
