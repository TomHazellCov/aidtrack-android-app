package com.tomhazell.aidtrackerapp;

import com.tomhazell.aidtrackerapp.additem.Item;
import com.tomhazell.aidtrackerapp.additem.Product;
import com.tomhazell.aidtrackerapp.additem.fragments.networking.CampaignService;
import com.tomhazell.aidtrackerapp.additem.fragments.networking.ItemService;
import com.tomhazell.aidtrackerapp.additem.fragments.networking.ProductService;
import com.tomhazell.aidtrackerapp.additem.fragments.networking.ShipmentService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tom Hazell on 27/03/2017.
 */

public class NetworkManager {
    public static final String BASE_URL = "http://api.aidtrack.donchev.net/index.php/api/";
    private static NetworkManager instance;
    private Retrofit retrofit;

    private CampaignService campaignService;
    private ShipmentService shipmentService;
    private ProductService productService;
    private ItemService itemService;

    private NetworkManager() {
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

    }

    public CampaignService getCampaignService(){
        if (campaignService == null) {
            campaignService = retrofit.create(CampaignService.class);
        }

        return campaignService;
    }

    public ShipmentService getShipmentService(){
        if (shipmentService == null) {
            shipmentService = retrofit.create(ShipmentService.class);
        }

        return shipmentService;
    }

    public ProductService getProductService(){
        if (productService == null) {
            productService = retrofit.create(ProductService.class);
        }

        return productService;
    }

    public ItemService getItemService(){
        if (itemService == null) {
            itemService = retrofit.create(ItemService.class);
        }

        return itemService;
    }


    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }

        return instance;
    }
}
