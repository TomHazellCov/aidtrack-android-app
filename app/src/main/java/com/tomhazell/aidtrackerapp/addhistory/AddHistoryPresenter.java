package com.tomhazell.aidtrackerapp.addhistory;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.tomhazell.aidtrackerapp.NetworkManager;
import com.tomhazell.aidtrackerapp.additem.ItemHistory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.tomhazell.aidtrackerapp.addhistory.AddHistoryActivity.PERMISSION_REQUEST_LOCATION_CODE;

/**
 * Created by Tom Hazell on 29/03/2017.
 */
public class AddHistoryPresenter {
    private final int itemId;
    private AddHistoryActivity activity;
    private Location location;
    private LocationManager locationManager;
    private LocationListener locationListener;

    private List<Disposable> disposables = new ArrayList<>();

    public AddHistoryPresenter(AddHistoryActivity activity, int itemId) {
        this.itemId = itemId;
        this.activity = activity;
        if (checkPerms()) {
            initGps();
        }
    }

    private boolean checkPerms() {
        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_LOCATION_CODE);
            return false;
        }
        return true;

    }

    private void initGps() {
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);//get last know location
        displayLocation();

        // Called when a new location is found by the network location provider.
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                AddHistoryPresenter.this.location = location;
                displayLocation();

                Log.d(getClass().getSimpleName(), "Got location " + location.getAccuracy());
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkPerms();
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 0, locationListener);
    }

    public void permissionResponse(boolean granted) {
        if (granted) {
            initGps();
        } else {
            checkPerms();
        }
    }

    private void displayLocation() {
        if (location != null) {
            activity.showLocation(location.getLatitude(), location.getLatitude(), location.getAccuracy(), SystemClock.elapsedRealtimeNanos() - location.getElapsedRealtimeNanos());
        }
    }

    public void save() {
        activity.loadingBarVisibility(true);
        activity.showError("");

        ItemHistory history = new ItemHistory();
        history.setStatus(activity.getStatus());
        history.setItemId(itemId);
        history.setLatitude(location.getLatitude());
        history.setLongitude(location.getLongitude());

        NetworkManager.getInstance().getItemHisotryService().addItemHistory(history)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ItemHistory>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(ItemHistory value) {
                        activity.onSaved();
                    }

                    @Override
                    public void onError(Throwable e) {
                        activity.showError("Error: " + e.toString());
                        Log.e(getClass().getSimpleName(), "Failed to update history", e);
                    }

                    @Override
                    public void onComplete() {}
                });

    }

    public void onStop() {
        locationManager.removeUpdates(locationListener);
        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
    }
}
