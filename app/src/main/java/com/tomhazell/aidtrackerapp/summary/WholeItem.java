package com.tomhazell.aidtrackerapp.summary;

import com.tomhazell.aidtrackerapp.additem.Campaign;
import com.tomhazell.aidtrackerapp.additem.Item;
import com.tomhazell.aidtrackerapp.additem.ItemHistory;
import com.tomhazell.aidtrackerapp.additem.Product;
import com.tomhazell.aidtrackerapp.additem.Shipment;

import java.util.List;

/**
 * Created by Tom Hazell on 07/03/2017.
 */

public class WholeItem {
//    private String itemName;
//    private String itemDescription;
//    private String itemManufacture;
//
//    private String campaignName;
//    private String campaignCreatorName;
//    private String shipmentName;
//    private String destination;

    private Campaign campaign;
    private Shipment shipment;
    private Product product;
    private Item item;

    public WholeItem() {
    }

    public WholeItem(Campaign campaign, Shipment shipment, Item item, Product product) {
        this.campaign = campaign;
        this.shipment = shipment;
        this.item = item;
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
