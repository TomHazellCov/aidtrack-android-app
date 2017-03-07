package com.tomhazell.aidtrackerapp.summary;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Tom Hazell on 07/03/2017.
 */
public class SummaryPresenter {

    private static final int VIEW_LOADING = 1;
    private static final int VIEW_CONTENT = 0;
    private String tagId;
    private SummaryActivity activity;
    private WholeItem item;

    public SummaryPresenter(SummaryActivity activity, String tagId) {
        this.activity = activity;
        this.tagId = tagId;

        //show loading stuff
        activity.setViewSwitcherItem(VIEW_LOADING);

        //init web stuff
        //TODO this is to mock it out
        WholeItem item = new WholeItem();
        item.setItemDescription("This item is very useful because it is good and a very good item and so useful and this is why im going to tell why why nw am i not?");
        item.setItemManufacture("Sample Manafacture");
        item.setItemName("Example Name");
        item.setShipmentName("Shipment 3");
        item.setCampaignName("Aid for here");
        item.setCampaignCreatorName("Red Cross");
        item.setDestination("Bristol");
        ItemTracking tracking = new ItemTracking(new Date(), "In the house", "Coventry");
        item.setTrackings(Arrays.asList(tracking, tracking, tracking, tracking));

        onGotProduct(item);
    }


    private void onGotProduct(WholeItem item){
        this.item = item;
        activity.displayItemData(this.item);
        activity.setViewSwitcherItem(VIEW_CONTENT);
    }
}
