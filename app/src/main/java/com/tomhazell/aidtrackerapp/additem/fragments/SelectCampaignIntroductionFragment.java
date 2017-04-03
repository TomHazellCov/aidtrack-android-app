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
import com.tomhazell.aidtrackerapp.additem.CampaignResponse;
import com.tomhazell.aidtrackerapp.additem.NewItem;

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
 * Created by tom on 18/03/17.
 */

public class SelectCampaignIntroductionFragment extends Fragment implements ValidatedFragment, AdapterView.OnItemSelectedListener {

    @BindView(R.id.select_campaign_viewswitcher)
    ViewSwitcher viewSwitcher;

    @BindView(R.id.select_campaign_name)
    TextInputLayout layoutName;

    @BindView(R.id.select_campaign_campaigns)
    Spinner selectCampaigns;

    @BindView(R.id.select_campaign_error)
    Button errorButton;

    @BindView(R.id.select_campaign_error_creating)
    TextView errorTextCreating;

    boolean gotExistingCampaigns = false;//have we received a list of campaigns
    boolean isCreatingCampaign = false;//is the user creating a new campaign or using an existing one
    boolean hasCreatedCampaign = false;

    private List<Campaign> campaigns;
    private Campaign selectedCampaign;

    private List<Disposable> disposables = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_additem_campaign, container, false);
        ButterKnife.bind(this, v);

        selectCampaigns.setOnItemSelectedListener(this);
        //create web request

        getAllCampaigns();

        return v;
    }

    @Override
    public void onStop() {
        //make sure all long running requests are stopped
        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
        super.onStop();
    }

    private void getAllCampaigns() {
        NetworkManager.getInstance().getCampaignService().getAllCampaigns()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Campaign>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(List<Campaign> value) {
                        onGotCampaigns(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorButton.setVisibility(View.VISIBLE);
                        Log.e(getClass().getSimpleName(), "Failed to get Campains", e);
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    void onGotCampaigns(List<Campaign> campaigns) {
        if (gotExistingCampaigns) {
            Log.w(this.getClass().getSimpleName(), "We got campains twice we should not be here");
            return;
        }
        gotExistingCampaigns = true;
        this.campaigns = campaigns;
        viewSwitcher.showNext();//show content not the progressbar

        //create array adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        adapter.add("Add a new campaign");//add default option

        //add all campaigns to it
        for (Campaign campaign : this.campaigns) {
            adapter.add(campaign.getName());
        }

        selectCampaigns.setAdapter(adapter);
        selectCampaigns.setSelection(0);
    }

    @OnClick(R.id.select_campaign_error)
    void setErrorButton(){
        errorButton.setVisibility(View.INVISIBLE);
        getAllCampaigns();
    }

    @Override
    public boolean validateDetails() {
        if (gotExistingCampaigns) {//&& !(!hasCreatedCampaign || (layoutName.getEditText().getText().toString().equals(selectedCampaign.getName()))) TODO
            if (selectCampaigns.getSelectedItemPosition() == 0 && selectedCampaign == null) {//if selectedCampain is null then we are creating one or we have already created one
                if (isCreatingCampaign){
                    return false;
                }

                //check feilds are correct then send to server
                if (layoutName.getEditText().getText().toString().equals("")) {
                    layoutName.setError("Cant be empty");
                    return false;
                }

                Campaign newCampaign = new Campaign(layoutName.getEditText().getText().toString());

                createCampaign(newCampaign);
                return false;

            } else {
                return true;
            }
        }

        return false;//check selected item, validate feilds if opeion selected
    }

    /**
     * calls the API to create a new campaign
     */
    private void createCampaign(Campaign newCampaign) {
        errorTextCreating.setVisibility(View.INVISIBLE);
        isCreatingCampaign = true;
        NetworkManager.getInstance().getCampaignService().createCampaign(newCampaign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CampaignResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(CampaignResponse value) {
                        onCampaignCreated(value.getInfo().get(0));
                        isCreatingCampaign = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorTextCreating.setVisibility(View.VISIBLE);
                        Log.e(getClass().getSimpleName(), "Failed to get Campains", e);
                        isCreatingCampaign = false;
                    }

                    @Override
                    public void onComplete() {}
                });
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

    void onCampaignCreated(Campaign campaign) {
        hasCreatedCampaign = true;
        this.selectedCampaign = campaign;
        ((AddItemActivity) getActivity()).onNextClick();
    }

    //for spinner selection
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        if (pos == 0) {
            layoutName.setVisibility(View.VISIBLE);

            selectedCampaign = null;
        } else {
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
