package com.github.bartrina.bootcamp.domain;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;

import com.github.bartrina.bootcamp.types.Location;

public final class SimpleLocator implements LocationProvider {

    private final LocationManager locationManager;
    private final String locationProvider;

    public SimpleLocator(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationProvider = locationManager.getBestProvider(new Criteria(), false);
    }

    @Override
    public Location getCurrentLocation() {
        try {
            android.location.Location loc = locationManager.getLastKnownLocation(locationProvider);
            return new Location(loc.getLatitude(), loc.getLongitude());
        } catch (SecurityException e) {
            throw e;
        }
    }
}
