package com.tomhazell.aidtrackerapp.additem;

import java.util.List;

/**
 * This is the model returned from the API when creating a campaign, we ignore the field shipment_name as we dont need it
 */
public class ItemResponse {
    private boolean status;

    private List<Item> info;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Item> getInfo() {
        return info;
    }

    public void setInfo(List<Item> info) {
        this.info = info;
    }
}
