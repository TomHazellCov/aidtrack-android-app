package com.tomhazell.aidtrackerapp.summary;

import android.nfc.Tag;
import android.util.Log;

import com.tomhazell.aidtrackerapp.NetworkManager;
import com.tomhazell.aidtrackerapp.additem.Campaign;
import com.tomhazell.aidtrackerapp.additem.Item;
import com.tomhazell.aidtrackerapp.additem.OuterCampaign;
import com.tomhazell.aidtrackerapp.additem.OuterItem;
import com.tomhazell.aidtrackerapp.additem.OuterShipment;
import com.tomhazell.aidtrackerapp.additem.Product;
import com.tomhazell.aidtrackerapp.additem.Shipment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tom Hazell on 07/03/2017.
 */
public class SummaryPresenter implements NfcCallback {

    private static final int VIEW_LOADING = 1;
    private static final int VIEW_CONTENT = 0;
    private String tagId;
    private SummaryActivity activity;
    private WholeItem item;

    private List<Disposable> disposables = new ArrayList<>();

    public SummaryPresenter(SummaryActivity activity, String tagId) {
        this.activity = activity;
        this.tagId = tagId;

        //show loading stuff
        activity.setViewSwitcherItem(VIEW_LOADING);
    }


    void getTagDetails(Tag tag) {
        //start the task that get the tag contents, it will callback to #onGotNfcMessage
        new NdefReaderTask(this).execute(tag);
    }


    private void onGotProduct(WholeItem item) {
        this.item = item;
        activity.displayItemData(this.item);
        activity.setViewSwitcherItem(VIEW_CONTENT);
    }

    @Override
    public void onGotNfcMessage(String message) {
        //TODO improve this by using a diffrent message type, but assume if tag is numeric it is ours
        int id;
        try {
            id = Integer.parseInt(message);
        } catch (NumberFormatException e) {
            onNfcError(new TagNotOursException());
            return;
        }
        activity.setLoadingText("Getting Item from server");

        //init web stuff
        //first get the item details
        NetworkManager.getInstance().getItemService().getItemById(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OuterItem>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(OuterItem value) {
                        getProductDetails(value.getItem());
                    }

                    @Override
                    public void onError(Throwable e) {
                        onNetworkError("Item", e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getProductDetails(final Item item) {
        activity.setLoadingText("Loading product details");

        NetworkManager.getInstance().getProductService().getProductById(item.getProductId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Product>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(Product value) {
                        getShipmentDetails(item, value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onNetworkError("Product", e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    private void getShipmentDetails(final Item item, final Product product) {
        activity.setLoadingText("Loading Shipment details");

        NetworkManager.getInstance().getShipmentService().getShipmentById(item.getShipmentId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OuterShipment>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(OuterShipment value) {
                        getCampaignDetails(item, product, value.getShipment().get(0));
                    }

                    @Override
                    public void onError(Throwable e) {
                        onNetworkError("shipment", e);
                    }

                    @Override
                    public void onComplete() {}
                });
    }


    private void getCampaignDetails(final Item item, final Product product, final Shipment shipment) {
        activity.setLoadingText("Loading Shipment details");

        NetworkManager.getInstance().getCampaignService().getCampaingById(shipment.getCampaignId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OuterCampaign>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(OuterCampaign value) {
                        onGotProduct(new WholeItem(value.getCampaign(), shipment, item, product));
                    }

                    @Override
                    public void onError(Throwable e) {
                        onNetworkError("Camapign", e);
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    private void onNetworkError(String name, Throwable e) {
        Log.e(getClass().getSimpleName(), "Network error", e);
        activity.setLoadingText("Error " + name + " ," + e.toString());
    }

    @Override
    public void onNfcError(Exception e) {
        if (e instanceof TagNotOursException) {
            //goto page to add new tag
            activity.navigateToAddItemActivity();
        } else if (e instanceof TagNotSupportedException) {
            //TODO show error on loading
        }
    }

    public void onStop() {
        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
    }
}
