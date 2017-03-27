package com.tomhazell.aidtrackerapp.additem;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tom Hazell on 21/03/2017.
 */

public class Shipment {

    private int id;

    @SerializedName(value="campaign_id")
    private int campaignId;

    @SerializedName(value="shipment_title")
    private String name;

    public Shipment(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(int campaignId) {
        this.campaignId = campaignId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
