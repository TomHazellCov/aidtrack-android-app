package com.tomhazell.aidtrackerapp.networking;

import com.tomhazell.aidtrackerapp.additem.ItemHistory;
import com.tomhazell.aidtrackerapp.additem.OuterCampaign;
import com.tomhazell.aidtrackerapp.additem.OuterItem;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Tom Hazell on 29/03/2017.
 */

public interface ItemHistoryService {
    @POST("v1/item_history")
    Observable<ItemHistory> addItemHistory(@Body ItemHistory history);

    @GET("v1/item_history/id/{id}")
    Observable<List<ItemHistory>> getItemHistoryByItemId(@Path("id") int id);


}
