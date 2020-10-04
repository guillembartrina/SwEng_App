package com.github.bartrina.bootcamp.domain;

import com.github.bartrina.bootcamp.types.WeatherReport;
import com.github.bartrina.bootcamp.types.Location;

import java.io.IOException;
import java.util.List;

public interface WeatherProvider {
    default void onCreation() {}
    default void onDestruction() {}

    List<WeatherReport> getForecast(Location location, int days) throws IOException;
}
