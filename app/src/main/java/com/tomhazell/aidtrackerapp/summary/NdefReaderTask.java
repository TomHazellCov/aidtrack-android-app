package com.tomhazell.aidtrackerapp.summary;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Code from https://code.tutsplus.com/tutorials/reading-nfc-tags-with-android--mobile-17278
 */
public class NdefReaderTask extends AsyncTask<Tag, Void, NdefRecord> {

    private NfcCallback callback;

    public NdefReaderTask(NfcCallback callback) {
        this.callback = callback;
    }

    @Override
    protected NdefRecord doInBackground(Tag... tags) {
        Tag tag = tags[0];

        Ndef ndef = Ndef.get(tag);
        if (ndef == null) {
            // NDEF is not supported by this Tag.
            return null;
        }

        NdefMessage ndefMessage = ndef.getCachedNdefMessage();

        NdefRecord[] records = ndefMessage.getRecords();
        for (NdefRecord ndefRecord : records) {
            if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                return ndefRecord;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(NdefRecord ndefRecord) {
        //TODO check format of the tag
        if (ndefRecord == null){
            callback.onNfcError();
        }else {
            callback.onGotNfcMessage(new NdefTagDescription(ndefRecord.getPayload()));
        }
    }
}
