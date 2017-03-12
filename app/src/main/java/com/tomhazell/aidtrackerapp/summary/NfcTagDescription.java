package com.tomhazell.aidtrackerapp.summary;

import java.nio.charset.StandardCharsets;

/**
 * Created by Tom Hazell on 07/03/2017.
 */

public class NfcTagDescription {

    private boolean isBlank;
    private String contents;

    public NfcTagDescription(byte[] content){
        if (content.length > 0) {
            this.contents = new String(content, StandardCharsets.UTF_8);
            isBlank = false;
        }else{
            isBlank = true;
        }
    }

    public boolean isBlank() {
        return isBlank;
    }

    public void setBlank(boolean blank) {
        isBlank = blank;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
