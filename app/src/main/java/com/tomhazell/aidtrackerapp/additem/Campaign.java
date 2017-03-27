package com.tomhazell.aidtrackerapp.additem;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tom on 18/03/17.
 */

public class Campaign {

    private int id;

    @SerializedName(value="campaign_name")
    private String name;

    @SerializedName(value="created_by")
    private int createdBy;

    public Campaign(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
}
