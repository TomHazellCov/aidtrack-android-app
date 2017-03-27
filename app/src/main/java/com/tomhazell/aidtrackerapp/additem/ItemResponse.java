package com.tomhazell.aidtrackerapp.additem;

/**
 * This is the model returned from the API when creating a campaign, we ignore the field shipment_name as we dont need it
 */
public class ItemResponse {
    private boolean status;

    private Item info;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Item getInfo() {
        return info;
    }

    public void setInfo(Item info) {
        this.info = info;
    }
}
