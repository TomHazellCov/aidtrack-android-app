package com.tomhazell.aidtrackerapp.additem;

import java.util.List;

/**
 * This is the model returned from the API when creating a campaign, we ignore the field shipment_name as we dont need it
 */
public class ShipmentResponse {
    private boolean status;

    private List<Shipment> info;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Shipment> getInfo() {
        return info;
    }

    public void setInfo(List<Shipment> info) {
        this.info = info;
    }
}
