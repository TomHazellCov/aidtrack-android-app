package com.tomhazell.aidtrackerapp.additem;

/**
 * Created by Tom Hazell on 17/02/2017.
 */

public class NewItem {
    private Product product;

    private Campaign campaign;

    private Shipment shipment;

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
