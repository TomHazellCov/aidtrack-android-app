package com.tomhazell.aidtrackerapp.additem;

/**
 * This is the model returned from the API when creating a campaign, we ignore the field shipment_name as we dont need it
 */
public class ProductResponse {
    private boolean status;

    private Product info;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Product getInfo() {
        return info;
    }

    public void setInfo(Product info) {
        this.info = info;
    }
}
