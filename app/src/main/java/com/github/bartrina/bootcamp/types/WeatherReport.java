package com.github.bartrina.bootcamp.types;

import java.net.URL;

public final class WeatherReport {
    public final long day;
    public final double temperature_min;
    public final double temperature_max;
    public final double humidity;
    public final double wind;
    public final double precipitation;
    public final URL icon;

    public WeatherReport(long day, double temperature_min, double temperature_max, double humidity, double wind, double precipitation, URL icon)
    {
        this.day = day;
        this.temperature_min = temperature_min;
        this.temperature_max = temperature_max;
        this.humidity = humidity;
        this.wind = wind;
        this.precipitation = precipitation;
        this.icon = icon;
    }
}
