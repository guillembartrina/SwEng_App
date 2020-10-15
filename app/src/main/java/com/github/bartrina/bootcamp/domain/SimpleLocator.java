package com.github.bartrina.bootcamp.domain;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;

import com.github.bartrina.bootcamp.types.Location;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public final class SimpleLocator implements LocationProvider {

    private final LocationManager locationManager;
    private final String locationProvider;

    @Inject
    public SimpleLocator(@ApplicationContext Context context) {
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
