package com.tomhazell.aidtrackerapp.additem.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.tomhazell.aidtrackerapp.NetworkManager;
import com.tomhazell.aidtrackerapp.R;
import com.tomhazell.aidtrackerapp.additem.AddItemActivity;
import com.tomhazell.aidtrackerapp.additem.Campaign;
import com.tomhazell.aidtrackerapp.additem.NewItem;
import com.tomhazell.aidtrackerapp.additem.Shipment;
import com.tomhazell.aidtrackerapp.additem.ShipmentResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tom Hazell on 21/03/2017.
 */
public class SelectShipmentIntroductionFragment extends Fragment implements ValidatedFragment, AdapterView.OnItemSelectedListener {

    @BindView(R.id.select_shipment_viewswitcher)
    ViewSwitcher viewSwitcher;

    @BindView(R.id.select_shipment_name)
    TextInputLayout layoutName;

    @BindView(R.id.select_shipment_shipments)
    Spinner selectShipment;

    @BindView(R.id.select_shipment_error)
    Button errorButton;

    @BindView(R.id.select_shipment_error_creating)
    TextView errorTextCreating;

    boolean gotExistingShipments = false;//have we received a list of shipments
    boolean isCreatingShipment = false;//is the user creating a new product or using an existing one
    boolean hasCreatedShipment = false;

    private List<Shipment> shipments;
    private Shipment selectedShipment;

    private List<Disposable> disposables = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_additem_shipment, container, false);
        ButterKnife.bind(this, v);

        selectShipment.setOnItemSelectedListener(this);
        //create web request

        getAllShipments();
        List<Shipment> shipments = new ArrayList<>();
        shipments.add(new Shipment("Name"));
        shipments.add(new Shipment("Name1"));
        onGotShipments(shipments);

        return v;
    }

    public void getAllShipments() {
        NetworkManager.getInstance().getShipmentService().getAllShipments()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Shipment>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(List<Shipment> value) {
                        onGotShipments(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorButton.setVisibility(View.VISIBLE);
                        Log.e(getClass().getSimpleName(), "Failed to get Campains", e);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    void onGotShipments(List<Shipment> shipments) {
        if (gotExistingShipments) {
            Log.w(this.getClass().getSimpleName(), "We got Shipments twice we should not be here");
            return;
        }

        gotExistingShipments = true;
        this.shipments = shipments;
        viewSwitcher.showNext();//show content not the progressbar

        //create array adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item);

        adapter.add("Add a new shipment");//add default option

        //add all shipments to it
        for (Shipment shipment : this.shipments) {
            adapter.add(shipment.getName());
        }

        selectShipment.setAdapter(adapter);
        selectShipment.setSelection(0);
    }

    /**
     * calls the API to create a new product
     */
    private void createShipment(Shipment shipment) {
        errorTextCreating.setVisibility(View.INVISIBLE);
        isCreatingShipment = true;
        NetworkManager.getInstance().getShipmentService().createShipment(shipment)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShipmentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(ShipmentResponse value) {
                        onShipmentCreated(value.getInfo().get(0));
                        isCreatingShipment = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorTextCreating.setVisibility(View.VISIBLE);
                        Log.e(getClass().getSimpleName(), "Failed to get Campains", e);
                        isCreatingShipment = false;
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    @OnClick(R.id.select_shipment_error)
    void onErrorClicked() {
        getAllShipments();
    }

    @Override
    public boolean validateDetails() {
        if (gotExistingShipments) {
            if (selectShipment.getSelectedItemPosition() == 0 && selectedShipment == null) {//!(!hasCreatedShipment || (layoutName.getEditText().getText().toString().equals(selectedShipment.getName()))) TODO
                if (isCreatingShipment) {
                    return true;
                }

                //check feilds are correct then send to server
                if (layoutName.getEditText().getText().toString().equals("")) {
                    layoutName.setError("Cant be empty");
                    return false;
                }
                if (!(getActivity() instanceof NewItemCallBack)) {
                    Log.e(getClass().getSimpleName(), "Activity dose not inherit NewItemCaLLBACK...? BAD THINGS");
                    return false;
                }

                Shipment newShip = new Shipment(layoutName.getEditText().getText().toString());
                newShip.setCampaignId(((NewItemCallBack) getActivity()).getItem().getCampaign().getId());
                createShipment(newShip);
                return false;

            } else {
                return true;
            }
        }

        return false;//check selected item, validate feilds if opeion selected
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public NewItem addDataToModel(NewItem newItem) {
        newItem.setShipment(selectedShipment);
        return newItem;
    }

    @Override
    public String getTitle() {
        return "Select Shipments";
    }

    void onShipmentCreated(Shipment shipment) {
        hasCreatedShipment = true;
        this.selectedShipment = shipment;
        ((AddItemActivity) getActivity()).onNextClick();
    }

    //for spinner selection
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        if (pos == 0) {
            layoutName.setVisibility(View.VISIBLE);

            selectedShipment = null;
        } else {
            layoutName.setVisibility(View.INVISIBLE);

            selectedShipment = shipments.get(pos - 1);
        }
    }

    //for spinner selection
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //do nothing
    }

    @Override
    public void onStop() {
        //make sure all long running requests are stopped
        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
        super.onStop();
    }


}
