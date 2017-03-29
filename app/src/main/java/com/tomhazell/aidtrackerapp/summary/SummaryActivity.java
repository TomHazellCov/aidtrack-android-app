package com.tomhazell.aidtrackerapp.summary;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.tomhazell.aidtrackerapp.R;
import com.tomhazell.aidtrackerapp.additem.AddItemActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

//TODO handle when refered form newItem thing
public class SummaryActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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

    @BindView(R.id.summary_loading_text)
    TextView loadingText;

    ItemHistoryAdapter adapter;

    SummaryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        historyRecycler.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(historyRecycler.getContext(),
                layoutManager.getOrientation());
        historyRecycler.addItemDecoration(dividerItemDecoration);


        presenter = new SummaryPresenter(this, "1");//TODO in future get id from tag...

//        handleIntent(getIntent());
        presenter.onGotNfcMessage("2");

    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    void displayItemData(WholeItem item) {
        adapter = new ItemHistoryAdapter(item.getItem().getHistory());
        historyRecycler.setAdapter(adapter);

        itemTitle.setText(item.getProduct().getName());
        itemDescription.setText(item.getProduct().getDescription());
        itemManufacturer.setText(item.getProduct().getManufacturer().getName());

        campaignCreatedBy.setText(item.getCampaign().getCreatedBy() + "TODO get user details");
        campaignName.setText(item.getCampaign().getName());
        shipment.setText(item.getShipment().getName());
        location.setText("Going to " + "? is this being added?");

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

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            // In case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();

            //check we support the correct tech
            for (String tech : techList) {
                if (tech.equals(Ndef.class.getName()) || tech.equals(NdefFormatable.class.getName())) {
                    presenter.getTagDetails(tag);
                    break;
                }
            }
        }

    }

    void navigateToAddItemActivity() {
        Intent i = new Intent(SummaryActivity.this, AddItemActivity.class);
        startActivity(i);
        finish();
    }

    void setLoadingText(String text){
        loadingText.setText(text);
    }
}
