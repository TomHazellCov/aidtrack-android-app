package com.tomhazell.aidtrackerapp.networking;

import com.tomhazell.aidtrackerapp.additem.Item;
import com.tomhazell.aidtrackerapp.additem.ItemResponse;
import com.tomhazell.aidtrackerapp.additem.OuterItem;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Tom Hazell on 26/03/2017.
 */

public interface ItemService {

    @GET("v1/items/id/{id}")
    Observable<OuterItem> getItemById(@Path("id") int id);

    @POST("v1/items")
    Observable<ItemResponse> createItem(@Body Item item);
}
