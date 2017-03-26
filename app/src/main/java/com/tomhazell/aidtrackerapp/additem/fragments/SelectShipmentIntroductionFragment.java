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
import android.widget.Spinner;
import android.widget.ViewSwitcher;

import com.tomhazell.aidtrackerapp.R;
import com.tomhazell.aidtrackerapp.additem.AddItemActivity;
import com.tomhazell.aidtrackerapp.additem.NewItem;
import com.tomhazell.aidtrackerapp.additem.Shipment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    boolean gotExistingShipments = false;//have we received a list of shipments
    boolean isCreatingShipments = true;//is the user creating a new product or using an existing one
    boolean hasCreatedShipment = false;

    private List<Shipment> shipments;
    private Shipment selectedShipment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_additem_shipment, container, false);
        ButterKnife.bind(this, v);

        selectShipment.setOnItemSelectedListener(this);
        //create web request (mock out from now)
        List<Shipment> shipments = new ArrayList<>();
        shipments.add(new Shipment("Name"));
        shipments.add(new Shipment("Name1"));
        onGotShipments(shipments);

        return v;
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item);

        adapter.add("Add a new product");//add default option

        //add all shipments to it
        for (Shipment shipment : this.shipments) {
            adapter.add(shipment.getName());
        }

        selectShipment.setAdapter(adapter);
        selectShipment.setSelection(0);
    }

    @Override
    public boolean validateDetails() {
        if (gotExistingShipments) {
            if (isCreatingShipments){
                if (hasCreatedShipment){
                    return true;
                }

                //check feilds are correct then send to server
                if (layoutName.getEditText().getText().toString().equals("")) {
                    layoutName.setError("Cant be empty");
                    return false;
                }

                //createProduct api call, then call onproductCreated TODO
                return false;

            }else{
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

    void onShipmentCreated(Shipment shipment){
        hasCreatedShipment = true;
        this.selectedShipment = shipment;
        ((AddItemActivity) getActivity()).onNextClick();
    }

    //for spinner selection
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        if (pos == 0){
            isCreatingShipments = true;
            layoutName.setVisibility(View.VISIBLE);

            selectedShipment = null;
        }else{
            isCreatingShipments = false;
            layoutName.setVisibility(View.INVISIBLE);

            selectedShipment = shipments.get(pos - 1);
        }
    }

    //for spinner selection
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //do nothing
    }
}