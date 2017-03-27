package com.tomhazell.aidtrackerapp.additem.fragments.networking;

import com.tomhazell.aidtrackerapp.additem.Manufacturer;
import com.tomhazell.aidtrackerapp.additem.Product;
import com.tomhazell.aidtrackerapp.additem.ProductResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Tom Hazell on 27/03/2017.
 */

public interface ManufacturesService {
    @GET("v1/manufacturers/id/{id}")
    Observable<Product> getManufacturerById(@Path("id") int id);

    @GET("v1/manufacturers")
    Observable<List<Manufacturer>> getAllManufacturers();

    @POST("v1/manufacturers")
    Observable<Manufacturer> createManufacturer(@Body Manufacturer manufacturer);

}
