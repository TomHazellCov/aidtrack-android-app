package com.tomhazell.aidtrackerapp.additem.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ViewSwitcher;

import com.tomhazell.aidtrackerapp.R;
import com.tomhazell.aidtrackerapp.additem.AddItemActivity;
import com.tomhazell.aidtrackerapp.additem.Campaign;
import com.tomhazell.aidtrackerapp.additem.NewItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tom on 18/03/17.
 */

public class SelectCampaignIntroductionFragment extends Fragment implements ValidatedFragment, AdapterView.OnItemSelectedListener {

    @BindView(R.id.select_campaign_viewswitcher)
    ViewSwitcher viewSwitcher;

    @BindView(R.id.select_campaign_name)
    TextInputLayout layoutName;

    @BindView(R.id.select_campaign_campaigns)
    Spinner selectCampaigns;

    boolean gotExistingCampaigns = false;//have we received a list of campaigns
    boolean isCreatingCampaigns = true;//is the user creating a new product or using an existing one
    boolean hasCreatedCampaign = false;

    private List<Campaign> campaigns;
    private Campaign selectedCampaign;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_additem_campaign, container, false);
        ButterKnife.bind(this, v);

        selectCampaigns.setOnItemSelectedListener(this);
        //create web request (mock out from now)
        List<Campaign> camps = new ArrayList<>();
        camps.add(new Campaign("Name"));
        camps.add(new Campaign("Name1"));
        onGotCampaigns(camps);

        return v;
    }

    void onGotCampaigns(List<Campaign> campaigns) {
        gotExistingCampaigns = true;
        this.campaigns = campaigns;
        viewSwitcher.showNext();//show content not the progressbar

        //create array adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item);

        adapter.add("Add a new product");//add default option

        //add all campaigns to it
        for (Campaign campaign : this.campaigns) {
            adapter.add(campaign.getName());
        }

        selectCampaigns.setAdapter(adapter);
        selectCampaigns.setSelection(0);
    }

    @Override
    public boolean validateDetails() {
        if (gotExistingCampaigns) {
            if (isCreatingCampaigns){
                if (hasCreatedCampaign){
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
        newItem.setCampaign(selectedCampaign);
        return newItem;
    }

    @Override
    public String getTitle() {
        return "Select Campaign";
    }

    void onCampaignCreated(Campaign campaign){
        hasCreatedCampaign = true;
        this.selectedCampaign = campaign;
        ((AddItemActivity) getActivity()).onNextClick();
    }

    //for spinner selection
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        if (pos == 0){
            isCreatingCampaigns = true;
            layoutName.setVisibility(View.VISIBLE);

            selectedCampaign = null;
        }else{
            isCreatingCampaigns = false;
            layoutName.setVisibility(View.INVISIBLE);

            selectedCampaign = campaigns.get(pos - 1);
        }
    }

    //for spinner selection
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //do nothing
    }
}
