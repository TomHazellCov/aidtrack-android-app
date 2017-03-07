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

    public void writeNfcTag(Tag tag, NdefMessage message) throws IOException, FormatException {
        if (tag != null) {
            Ndef ndefTag = Ndef.get(tag);

            if (ndefTag == null) {
                // Let's try to format the Tag in NDEF
                NdefFormatable nForm = NdefFormatable.get(tag);
                if (nForm != null) {
                    nForm.connect();
                    nForm.format(message);
                    nForm.close();
                }
            } else {
                ndefTag.connect();
                ndefTag.writeNdefMessage(message);
                ndefTag.close();
            }
        }
    }


    public NdefMessage stringToNdefMessage(String content) throws UnsupportedEncodingException {
        // Get UTF-8 byte
        byte[] lang = Locale.getDefault().getLanguage().getBytes("UTF-8");
        byte[] text = content.getBytes("UTF-8"); // Content in UTF-8

        int langSize = lang.length;
        int textLength = text.length;

        ByteArrayOutputStream payload = new ByteArrayOutputStream(1 + langSize + textLength);
        payload.write((byte) (langSize & 0x1F));
        payload.write(lang, 0, langSize);
        payload.write(text, 0, textLength);
        NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload.toByteArray());
        return new NdefMessage(new NdefRecord[]{record});
    }
}
