package com.github.bartrina.bootcamp.domain;

import com.github.bartrina.bootcamp.types.Location;
import com.github.bartrina.bootcamp.types.Address;

import java.io.IOException;

public interface GeocodingProvider {
    Location address2Location(Address address) throws IOException;
    Address location2Address(Location location) throws IOException;
}
