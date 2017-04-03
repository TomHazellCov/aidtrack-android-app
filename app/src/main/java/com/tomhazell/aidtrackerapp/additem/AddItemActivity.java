package com.tomhazell.aidtrackerapp.additem;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tomhazell.aidtrackerapp.R;
import com.tomhazell.aidtrackerapp.additem.fragments.CreateProductIntroductionFragment;
import com.tomhazell.aidtrackerapp.additem.fragments.FragmentVis;
import com.tomhazell.aidtrackerapp.additem.fragments.NewItemCallBack;
import com.tomhazell.aidtrackerapp.additem.fragments.NewTagIntroductionFragment;
import com.tomhazell.aidtrackerapp.additem.fragments.NfcListener;
import com.tomhazell.aidtrackerapp.additem.fragments.SelectCampaignIntroductionFragment;
import com.tomhazell.aidtrackerapp.additem.fragments.SelectProductIntroductonFragment;
import com.tomhazell.aidtrackerapp.additem.fragments.SelectShipmentIntroductionFragment;
import com.tomhazell.aidtrackerapp.additem.fragments.ValidatedFragment;
import com.tomhazell.aidtrackerapp.summary.SummaryActivity;
import com.tomhazell.aidtrackerapp.widget.UnscrollableViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tom Hazell on 16/02/2017.
 */

public class AddItemActivity extends AppCompatActivity implements NewItemCallBack {

    @BindView(R.id.startupCoordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.startupViewPager)
    UnscrollableViewPager viewPager;

    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.startupBack)
    Button back;

    @BindView(R.id.startupNext)
    Button next;

    private List<ValidatedFragment> fragments = new ArrayList<>();

    private AddItemPresenter presenter;
    private AddItemAdapter addItemAdapter;

    NfcAdapter mAdapter;
    PendingIntent mPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        //display backarrow in top actionbar
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

        //get Bundle
        boolean newTag = getIntent().getBooleanExtra(SummaryActivity.EXTRA_NEW_TAG, false);
        boolean badDataTag = getIntent().getBooleanExtra(SummaryActivity.EXTRA_TAG_BADID, false);

        //create bundle for introFragment
        Bundle introBundle = new Bundle();
        introBundle.putBoolean(SummaryActivity.EXTRA_NEW_TAG, newTag);
        introBundle.putBoolean(SummaryActivity.EXTRA_TAG_BADID, badDataTag);

        //add bundle to fragment
        NewTagIntroductionFragment introductionFragment = new NewTagIntroductionFragment();
        introductionFragment.setArguments(introBundle);

        //first add all framgnets
        fragments.add(introductionFragment);
        fragments.add(new SelectProductIntroductonFragment());
        fragments.add(new SelectCampaignIntroductionFragment());
        fragments.add(new SelectShipmentIntroductionFragment());
        fragments.add(new CreateProductIntroductionFragment());

        presenter = new AddItemPresenter(this, fragments.size());
        setDefaultTitle();

        addItemAdapter = new AddItemAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(addItemAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //Unused
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //Show finish if we are on the 3rd page and hide back if we are on the first
            @Override
            public void onPageSelected(int position) {
                presenter.onPageSelected(position);
                ValidatedFragment validatedFragment = fragments.get(position);
                if (validatedFragment instanceof FragmentVis){
                    FragmentVis fragment = (FragmentVis) validatedFragment;
                    if (fragment != null) {
                        fragment.visible();
                    }

                }
            }

            //unused
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            //nfc not support your device.
            return;
        }
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);

    }

    @Override
    protected void onPause() {
        if (mAdapter != null) {
            mAdapter.disableForegroundDispatch(this);
        }
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // Tell the fragment we have a tag if needed
        Log.w(getClass().getSimpleName(), "New Intentt");
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Fragment fragment = addItemAdapter.getItem(getCurrentPage());
        if (fragment instanceof NfcListener && tag != null){
            NfcListener listener = (NfcListener) fragment;
            listener.onTagDiscovered(tag);
        }
    }

    @OnClick(R.id.startupNext)
    public void onNextClick() {
        presenter.onNextClick();
    }

    @OnClick(R.id.startupBack)
    public void onBackClick() {
        presenter.onBackClick();
    }

    boolean validatePage(int pos){
        return addItemAdapter.validatePage(pos);
    }

    void setDefaultTitle() {
        collapsingToolbarLayout.setTitle(getString(R.string.add_item_title));
    }

    void setTitle(String title){
        collapsingToolbarLayout.setTitle(title);
    }

    int getCurrentPage() {
        return viewPager.getCurrentItem();
    }

    void setPage(int page) {
        viewPager.setCurrentItem(page);
        setTitle(fragments.get(page).getTitle());
    }

    void showBackButton(boolean showBack) {
        back.setVisibility(showBack ? View.VISIBLE : View.INVISIBLE);
    }

    void showFinishButton(boolean showFinish) {
        if (showFinish) {
            next.setText(R.string.finish);
        } else {
            next.setText(R.string.next);
        }
    }

    void hideNavigation(boolean hideButtons) {
        next.setVisibility(hideButtons ? View.INVISIBLE : View.VISIBLE);
    }

    void showSnackBar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public NewItem getItem() {
        return addItemAdapter.createModel();
    }
}
