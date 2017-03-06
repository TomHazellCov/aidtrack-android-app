package com.tomhazell.aidtrackerapp.additem;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.tomhazell.aidtrackerapp.R;
import com.tomhazell.aidtrackerapp.additem.fragments.NewTagIntroductionFragment;
import com.tomhazell.aidtrackerapp.additem.fragments.SelectProductIntroductonFragment;
import com.tomhazell.aidtrackerapp.additem.fragments.ValidatedFragment;
import com.tomhazell.aidtrackerapp.widget.UnscrollableViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom Hazell on 16/02/2017.
 */

public class AddItemActivity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;

    UnscrollableViewPager viewPager;

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    Button back;
    Button next;

    private List<ValidatedFragment> fragments = new ArrayList<>();

    private AddItemPresenter presenter;
    private AddItemAdapter addItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        init();
        setSupportActionBar(toolbar);

        //display backarrow in top actionbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //first add all framgnets
        fragments.add(new NewTagIntroductionFragment());
        fragments.add(new SelectProductIntroductonFragment());

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
            }

            //unused
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    private void init() {
        next = (Button) findViewById(R.id.startupNext);
        back = (Button) findViewById(R.id.startupBack);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        viewPager = (UnscrollableViewPager) findViewById(R.id.startupViewPager);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.startupCoordinator);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNextClick();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackClick();
            }
        });
    }

    public void onNextClick() {
        presenter.onNextClick();
    }

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

}
