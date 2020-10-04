package com.github.bartrina.bootcamp.domain;

import com.github.bartrina.bootcamp.types.Location;

public interface LocationProvider {
    Location getCurrentLocation();
}
