package com.github.bartrina.bootcamp.domain;

import android.content.Context;
import android.location.Geocoder;

import com.github.bartrina.bootcamp.types.Location;
import com.github.bartrina.bootcamp.types.Address;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;

public final class SimpleGeocoder implements GeocodingProvider {

    private final Geocoder geocoder;

    @Inject
    public SimpleGeocoder(@ApplicationContext Context context) {
        geocoder = new Geocoder(context);
    }

    @Override
    public Location address2Location(Address address) throws IOException {
        android.location.Address addr = geocoder.getFromLocationName(address.toString(), 1).get(0);
        return new Location(addr.getLatitude(), addr.getLongitude());
    }

    @Override
    public Address location2Address(Location location) throws IOException {
        android.location.Address addr = geocoder.getFromLocation(location.lat, location.lon, 1).get(0);
        List<String> lines = new ArrayList<>();
        for(int i = 0; i <= addr.getMaxAddressLineIndex(); i++) {
            lines.add(addr.getAddressLine(i));
        }
        return new Address(lines);
    }
}
