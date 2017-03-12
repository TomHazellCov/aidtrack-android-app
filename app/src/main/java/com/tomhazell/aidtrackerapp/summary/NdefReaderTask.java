package com.tomhazell.aidtrackerapp.summary;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.AsyncTask;

import java.util.Arrays;

/**
 * Code from https://code.tutsplus.com/tutorials/reading-nfc-tags-with-android--mobile-17278
 */
public class NdefReaderTask extends AsyncTask<Tag, Void, NdefRecord> {

    private NfcCallback callback;

    private Exception exception;

    public NdefReaderTask(NfcCallback callback) {
        this.callback = callback;
    }

    @Override
    protected NdefRecord doInBackground(Tag... tags) {
        Tag tag = tags[0];

        Ndef ndef = Ndef.get(tag);
        NdefFormatable ndefFormatable = NdefFormatable.get(tag);
        if (ndef == null && ndefFormatable == null) {
            // NDEF is not supported by this Tag.
            exception = new TagNotSupportedException();
            return null;
        } else if (ndef == null) {
            //NDEF is supported but it is formatted incorrectly
            exception = new TagNotOursException();
            return null;
        } else {
            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    return ndefRecord;
                }
            }

            exception = new TagNotOursException();
            return null;
        }
    }

    @Override
    protected void onPostExecute(NdefRecord ndefRecord) {
        if (ndefRecord == null) {
            callback.onNfcError(exception);
        } else {
            callback.onGotNfcMessage(new NfcTagDescription(ndefRecord.getPayload()));
        }
    }
}
