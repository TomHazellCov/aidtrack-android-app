package com.tomhazell.aidtrackerapp.networking;

import com.tomhazell.aidtrackerapp.additem.OuterShipment;
import com.tomhazell.aidtrackerapp.additem.Shipment;
import com.tomhazell.aidtrackerapp.additem.ShipmentResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Tom Hazell on 26/03/2017.
 */
public interface ShipmentService {

    @GET("v1/shipments/id/{id}")
    Observable<OuterShipment> getShipmentById(@Path("id") int id);

    @GET("v1/shipments")
    Observable<List<Shipment>> getAllShipments();

    @POST("v1/shipments")
    Observable<ShipmentResponse> createShipment(@Body Shipment shipment);
}
