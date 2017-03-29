package com.tomhazell.aidtrackerapp.additem;

import java.util.List;

/**
 * Created by Tom Hazell on 27/03/2017.
 */

public class OuterShipment {
    private List<Shipment> shipment;

    public List<Shipment> getShipment() {
        return shipment;
    }

    public void setShipment(List<Shipment> shipment) {
        this.shipment = shipment;
    }
}
