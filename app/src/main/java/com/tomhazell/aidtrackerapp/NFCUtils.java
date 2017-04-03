package com.tomhazell.aidtrackerapp;

import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;

import com.tomhazell.aidtrackerapp.summary.TagNotSupportedException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 * Created by Tom Hazell on 12/03/2017.
 */

public class NFCUtils {

    public static NdefMessage stringToNdefMessage(String content) throws UnsupportedEncodingException {
        // Get UTF-8 byte
//        byte[] lang = Locale.getDefault().getLanguage().getBytes("UTF-8");
        byte[] text = content.getBytes("UTF-8"); // Content in UTF-8

//        int langSize = lang.length;
        int textLength = text.length;

        ByteArrayOutputStream payload = new ByteArrayOutputStream(textLength);
//        payload.write((byte) (langSize & 0x1F));
//        payload.write(lang, 0, langSize);
        payload.write(text, 0, textLength);
        NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload.toByteArray());
        return new NdefMessage(new NdefRecord[]{record});
    }

    public static void writeNfcTag(Tag tag, NdefMessage message) throws IOException, FormatException, TagNotSupportedException {
        if (tag != null) {
            Ndef ndefTag = Ndef.get(tag);

            if (ndefTag == null) {
                // Let's try to format the Tag in NDEF
                NdefFormatable nForm = NdefFormatable.get(tag);
                if (nForm != null) {
                    nForm.connect();
                    nForm.format(message);
                    nForm.close();
                }else{
                    throw new TagNotSupportedException();
                }
            } else {
                ndefTag.connect();
                ndefTag.writeNdefMessage(message);
                ndefTag.close();
            }
        }
    }


}
