package com.tomhazell.aidtrackerapp.additem.fragments;

import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tomhazell.aidtrackerapp.R;
import com.tomhazell.aidtrackerapp.additem.NewItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import butterknife.ButterKnife;

/**
 * Created by Tom Hazell on 27/02/2017.
 */

public class WriteTagIntroductionFragment extends Fragment implements ValidatedFragment, NfcListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_additem_intro, container, false);
        ButterKnife.bind(this, v);

        //TODO send web request, convert to ndef message

        //when done then read and stuff
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
        return "Write to tag";
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        //if we have made network request only then:
        //TODO write to TAG

    }



}
