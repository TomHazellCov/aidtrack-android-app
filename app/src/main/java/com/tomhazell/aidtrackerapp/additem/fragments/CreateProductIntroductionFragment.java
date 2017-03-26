package com.tomhazell.aidtrackerapp.additem.fragments;

import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.tomhazell.aidtrackerapp.NFCUtils;
import com.tomhazell.aidtrackerapp.R;
import com.tomhazell.aidtrackerapp.additem.NewItem;
import com.tomhazell.aidtrackerapp.summary.TagNotSupportedException;

import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tom Hazell on 25/03/2017.
 */

public class CreateProductIntroductionFragment extends Fragment implements ValidatedFragment, NfcListener {

    public static final int FLIPPER_SCAN_NFC = 1;
    public static final int FLIPPER_SCAN_DONE = 2;
    public static final int FLIPPER_SCAN_START = 0;
    public static final int FLIPPER_SCAN_ERROR = 3;


    private NewItem newItem;
    private int id;
    private boolean itemCreated = false;
    private boolean tagWriten = false;

    @BindView(R.id.create_product_flipper)
    ViewFlipper flipper;

    @BindView(R.id.create_product_NFC_error)
    TextView nfcError;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_additem_create, container, false);//TODO
        ButterKnife.bind(this, v);

        //first get the item
        newItem = ((NewItemCallBack) getActivity()).getItem();

        //create the network request
        //TODO mock it out
        //write to tag
        onSavedItem(123);

        return v;
    }

    @Override
    public void onTagDiscovered(final Tag tag) {
        if (itemCreated) {
            Completable.create(new CompletableOnSubscribe() {
                @Override
                public void subscribe(CompletableEmitter e) throws Exception {
                    NdefMessage ndefMessage;
                    try {
                        ndefMessage = NFCUtils.stringToNdefMessage(String.valueOf(id));
                        NFCUtils.writeNfcTag(tag, ndefMessage);
                        e.onComplete();
                    } catch (FormatException | TagNotSupportedException | IOException e1) {
                        e.onError(e1);
                    }
                }
            })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //becuase this should not take long we will assume it wont stay for to long
                        //TODO actualy consolidate this with the one from Rx retrofit
                    }

                    @Override
                    public void onComplete() {
                        flipper.setDisplayedChild(FLIPPER_SCAN_DONE);
                        tagWriten = true;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(this.getClass().getSimpleName(), "Error writing to NFC tag", e);
                        nfcError.setText("Error writing to NFC tag " + e.getClass().getSimpleName() + " try again...");
                    }
                });
        }
    }


    private void onSavedItem(int id) {
        flipper.setDisplayedChild(FLIPPER_SCAN_NFC);
        itemCreated = true;
        this.id = id;
    }


    //All from ValidatedFragment
    @Override
    public boolean validateDetails() {
        return tagWriten;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public NewItem addDataToModel(NewItem newItem) {
        return newItem;
    }

    @Override
    public String getTitle() {
        return "Creating Tag...";
    }

}
