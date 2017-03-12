package com.tomhazell.aidtrackerapp.summary;

import android.nfc.Tag;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Tom Hazell on 07/03/2017.
 */
public class SummaryPresenter implements NfcCallback {

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

    }


    void getTagDetails(Tag tag){
        //start the task that get the tag contents, it will callback to #onGotNfcMessage
        new NdefReaderTask(this).execute(tag);
    }


    private void onGotProduct(WholeItem item){
        this.item = item;
        activity.displayItemData(this.item);
        activity.setViewSwitcherItem(VIEW_CONTENT);
    }

    @Override
    public void onGotNfcMessage(NfcTagDescription message) {
        //TODO make network request with the tag content
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

    @Override
    public void onNfcError(Exception e) {
        if (e instanceof TagNotOursException){
            //goto page to add new tag
            activity.navigateToAddItemActivity();
        }else if (e instanceof TagNotSupportedException){
            //TODO show error on loading
        }
    }

}
