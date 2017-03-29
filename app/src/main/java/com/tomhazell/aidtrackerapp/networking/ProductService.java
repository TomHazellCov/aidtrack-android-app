package com.tomhazell.aidtrackerapp.networking;

import com.tomhazell.aidtrackerapp.additem.Product;
import com.tomhazell.aidtrackerapp.additem.ProductResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Tom Hazell on 26/03/2017.
 */

public interface ProductService {

    @GET("v1/products/id/{id}")
    Observable<Product> getProductById(@Path("id") int id);

    @GET("v1/products")
    Observable<List<Product>> getAllProducts();

    @POST("v1/products")
    Observable<ProductResponse> createProduct(@Body Product product);
}
