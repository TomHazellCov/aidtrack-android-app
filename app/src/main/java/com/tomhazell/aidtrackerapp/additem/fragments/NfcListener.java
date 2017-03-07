package com.tomhazell.aidtrackerapp.additem.fragments;

import android.nfc.Tag;

/**
 * Created by Tom Hazell on 07/03/2017.
 */

public interface NfcListener {

    void onTagDiscovered(Tag tag);
}
