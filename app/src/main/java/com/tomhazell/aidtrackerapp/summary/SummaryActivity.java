package com.tomhazell.aidtrackerapp.summary;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.tomhazell.aidtrackerapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SummaryActivity extends AppCompatActivity {

    @BindView(R.id.summaryHistory)
    RecyclerView historyRecycler;

    @BindView(R.id.summarySwitcher)
    ViewSwitcher viewSwitcher;

    @BindView(R.id.summaryItemTitle)
    TextView itemTitle;

    @BindView(R.id.summaryItemDesc)
    TextView itemDescription;

    @BindView(R.id.summaryItemManafact)
    TextView itemManufacturer;

    @BindView(R.id.summaryShipment)
    TextView shipment;

    @BindView(R.id.summaryShipmentCreatedBy)
    TextView campaignCreatedBy;

    @BindView(R.id.summaryShipmentName)
    TextView campaignName;

    @BindView(R.id.summaryLocation)
    TextView location;

    ItemHistoryAdapter adapter;

    SummaryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ButterKnife.bind(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        historyRecycler.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(historyRecycler.getContext(),
                layoutManager.getOrientation());
        historyRecycler.addItemDecoration(dividerItemDecoration);


        presenter = new SummaryPresenter(this, "1");//TODO in future get id from tag...

        handleIntent(getIntent());

    }

    void displayItemData(WholeItem item) {
        adapter = new ItemHistoryAdapter(item.getTrackings());
        historyRecycler.setAdapter(adapter);

        itemTitle.setText(item.getItemName());
        itemDescription.setText(item.getItemDescription());
        itemManufacturer.setText(item.getItemManufacture());

        campaignCreatedBy.setText(item.getCampaignCreatorName());
        campaignName.setText(item.getCampaignName());
        shipment.setText(item.getShipmentName());
        location.setText("Going to " + item.getDestination());

    }

    void setViewSwitcherItem(int pos) {
        switch (pos) {
            case 1:
                viewSwitcher.showNext();
                break;
            case 0:
                viewSwitcher.showPrevious();
                break;
            default:
                viewSwitcher.showPrevious();
                break;
        }
    }

    private void handleIntent(Intent intent){
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            // In case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            //check we support the correct tech
            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    presenter.getTagDetails(tag);
                    break;
                }
            }
        }

    }
}
