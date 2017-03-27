package com.tomhazell.aidtrackerapp.additem;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tom Hazell on 27/03/2017.
 */

public class Manufacturer {
    private int id;

    @SerializedName(value="manufacturer_name")
    private String name;

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
}
