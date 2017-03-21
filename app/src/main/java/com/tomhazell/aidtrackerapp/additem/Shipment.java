package com.tomhazell.aidtrackerapp.additem;

/**
 * Created by Tom Hazell on 21/03/2017.
 */

public class Shipment {
    private String name;

    public Shipment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
