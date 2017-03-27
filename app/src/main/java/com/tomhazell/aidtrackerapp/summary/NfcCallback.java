package com.tomhazell.aidtrackerapp.summary;

/**
 * Created by Tom Hazell on 07/03/2017.
 */

public interface NfcCallback {

    void onGotNfcMessage(String message);

    /**
     * Can take, tagNotOursException or TagNotSupportedException
     * @param e the error
     */
    void onNfcError(Exception e);
}
