package com.tomhazell.aidtrackerapp;

import com.tomhazell.aidtrackerapp.additem.Item;
import com.tomhazell.aidtrackerapp.additem.Product;
import com.tomhazell.aidtrackerapp.additem.fragments.networking.CampaignService;
import com.tomhazell.aidtrackerapp.additem.fragments.networking.ItemService;
import com.tomhazell.aidtrackerapp.additem.fragments.networking.ManufacturesService;
import com.tomhazell.aidtrackerapp.additem.fragments.networking.ProductService;
import com.tomhazell.aidtrackerapp.additem.fragments.networking.ShipmentService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
    private ManufacturesService manufacturesService;

    private NetworkManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        retrofit = new Retrofit.Builder()
                .client(client)
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

    public ManufacturesService getManufacturesService(){
        if (manufacturesService == null) {
            manufacturesService = retrofit.create(ManufacturesService.class);
        }

        return manufacturesService;
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
