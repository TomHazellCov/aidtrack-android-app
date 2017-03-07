package com.tomhazell.aidtrackerapp.summary;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;

/**
 * Created by Tom Hazell on 07/03/2017.
 */

public interface NfcCallback {

    void onGotNfcMessage(NdefTagDescription message);
    void onNfcError();
}
