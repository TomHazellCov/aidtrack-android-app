package com.tomhazell.aidtrackerapp.addhistory;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tomhazell.aidtrackerapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tom Hazell on 29/03/2017.
 */
public class AddHistoryActivity extends AppCompatActivity {

    final static int PERMISSION_REQUEST_LOCATION_CODE = 123;
    public static final String EXTRA_ITEM_ID = "EXTRA_ITEM_ID";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_history_status)
    TextInputLayout statusInput;
    @BindView(R.id.add_history_location)
    TextView locationText;
    @BindView(R.id.add_history_location_sub)
    TextView locationAccuracyText;
    @BindView(R.id.add_history_progress)
    ProgressBar progress;
    @BindView(R.id.add_history_error)
    TextView error;

    private AddHistoryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_history);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int itemId = getIntent().getIntExtra(EXTRA_ITEM_ID, -1);
        if (itemId == -1) {
            Log.wtf(getClass().getSimpleName(), "ItemId cant be -1");
            finish();
        }

        presenter = new AddHistoryPresenter(this, itemId);
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.history_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.done:
                presenter.save();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.permissionResponse(true);
                } else {
                    presenter.permissionResponse(false);
                }
            }
        }
    }

    public void loadingBarVisibility(boolean isVisible) {
        progress.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public String getStatus() {
        return statusInput.getEditText().getText().toString();
    }

    public void showError(String text) {
        error.setText(text);
    }

    public void showLocation(double lat, double log, float accuracy, long ageInNanos) {
        long age = ageInNanos / 1000000000L;// in seconds

        locationText.setText("lat: " + lat + " long: " + log);

        //only show time since hit if it is more than a min
        if (age > 60) {
            locationAccuracyText.setText("+-" + accuracy + " meters , " + age / 60 + " mins ago");
        } else {
            locationAccuracyText.setText("+-" + accuracy + " meters");
        }
    }

    public void onSaved() {
        finish();
    }
}
