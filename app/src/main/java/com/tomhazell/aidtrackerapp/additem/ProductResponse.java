package com.tomhazell.aidtrackerapp.additem;

import java.util.List;

/**
 * This is the model returned from the API when creating a campaign, we ignore the field shipment_name as we dont need it
 */
public class ProductResponse {
    private boolean status;

    private List<Product> info;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Product> getInfo() {
        return info;
    }

    public void setInfo(List<Product> info) {
        this.info = info;
    }
}
