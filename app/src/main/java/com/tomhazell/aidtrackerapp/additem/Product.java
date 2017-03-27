package com.tomhazell.aidtrackerapp.additem;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tom Hazell on 27/02/2017.
 */

public class Product {
    private int id;

    @SerializedName(value="product_name")
    private String name;
    private String type;

    public Product(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Product() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
