package com.tomhazell.aidtrackerapp.summary;

import java.util.List;

/**
 * Created by Tom Hazell on 07/03/2017.
 */

public class WholeItem {
    private List<ItemTracking> trackings;

    private String itemName;
    private String itemDescription;
    private String itemManufacture;

    private String campaignName;
    private String campaignCreatorName;
    private String shipmentName;
    private String destination;


    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getCampaignCreatorName() {
        return campaignCreatorName;
    }

    public void setCampaignCreatorName(String campaignCreatorName) {
        this.campaignCreatorName = campaignCreatorName;
    }

    public String getShipmentName() {
        return shipmentName;
    }

    public void setShipmentName(String shipmentName) {
        this.shipmentName = shipmentName;
    }

    public List<ItemTracking> getTrackings() {
        return trackings;
    }

    public void setTrackings(List<ItemTracking> trackings) {
        this.trackings = trackings;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemManufacture() {
        return itemManufacture;
    }

    public void setItemManufacture(String itemManufacture) {
        this.itemManufacture = itemManufacture;
    }
}
