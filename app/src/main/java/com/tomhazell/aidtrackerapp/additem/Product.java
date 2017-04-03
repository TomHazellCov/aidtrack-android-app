package com.tomhazell.aidtrackerapp.additem;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tom Hazell on 27/02/2017.
 */

public class Product {
    private int id;

    @SerializedName(value="product_name")
    private String name;

    @SerializedName(value="product_description")
    private String description;

    private Manufacturer manufacturer;

    @SerializedName(value="manufacturer_id")
    private int mId;

    public Product(String name, String type) {
        this.name = name;
        this.description = type;
    }

    public Product() {
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
        mId = manufacturer.getId();
    }
}
