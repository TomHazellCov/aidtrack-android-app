package com.tomhazell.aidtrackerapp.additem;

/**
 * This is the model returned from the API when creating a campaign, we ignore the field shipment_name as we dont need it
 */
public class ShipmentResponse {
    private boolean status;

    private Shipment info;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Shipment getInfo() {
        return info;
    }

    public void setInfo(Shipment info) {
        this.info = info;
    }
}
