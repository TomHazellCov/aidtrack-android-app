package com.tomhazell.aidtrackerapp.additem;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tom on 27/03/17.
 */

public class Item {

    private int id;

    @SerializedName(value = "item_nfc")
    private String nfc;

    @SerializedName(value="product_id")
    private int productId;

    @SerializedName(value="shipment_id")
    private int shipmentId;

    public Item(String nfc, int productId, int shipmentId) {
        this.nfc = nfc;
        this.productId = productId;
        this.shipmentId = shipmentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNfc() {
        return nfc;
    }

    public void setNfc(String nfc) {
        this.nfc = nfc;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }
}
