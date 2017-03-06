package com.tomhazell.aidtrackerapp.additem.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tomhazell.aidtrackerapp.R;
import com.tomhazell.aidtrackerapp.additem.NewItem;

import butterknife.ButterKnife;

/**
 * Created by Tom Hazell on 27/02/2017.
 */

public class NewTagIntroductionFragment extends Fragment implements ValidatedFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_additem_intro, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public boolean validateDetails() {
        return true;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public NewItem addDataToModel(NewItem newItem) {
        return newItem;
    }

    @Override
    public String getTitle() {
        return "Adding a new title";
    }
}
