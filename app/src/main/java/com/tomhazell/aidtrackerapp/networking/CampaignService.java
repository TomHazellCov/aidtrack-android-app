package com.tomhazell.aidtrackerapp.networking;

import com.tomhazell.aidtrackerapp.additem.Campaign;
import com.tomhazell.aidtrackerapp.additem.CampaignResponse;
import com.tomhazell.aidtrackerapp.additem.OuterCampaign;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Tom Hazell on 26/03/2017.
 */

public interface CampaignService {

    @GET("v1/campaigns/id/{id}")
    Observable<OuterCampaign> getCampaingById(@Path("id") int id);

    @GET("v1/campaigns")
    Observable<List<Campaign>> getAllCampaigns();

    @POST("v1/campaigns")
    Observable<CampaignResponse> createCampaign(@Body Campaign campaign);
}
