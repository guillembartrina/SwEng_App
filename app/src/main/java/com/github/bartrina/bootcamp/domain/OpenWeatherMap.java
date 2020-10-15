package com.github.bartrina.bootcamp.domain;

import com.github.bartrina.bootcamp.data.HTTPRequester;
import com.github.bartrina.bootcamp.data.SimpleHTTPRequester;
import com.github.bartrina.bootcamp.types.WeatherReport;
import com.github.bartrina.bootcamp.types.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public final class OpenWeatherMap implements WeatherProvider {
    private final String key;
    private static final String URL = "https://api.openweathermap.org/data/2.5/onecall";
    private static final String UNITS = "metric";
    private static final String EXCLUDE = "minutely,hourly,alerts";
    private static final String ICON_URL = "https://openweathermap.org/img/wn/";

    private HTTPRequester requester = new SimpleHTTPRequester();

    @Inject
    public OpenWeatherMap(String key) {
        this.key = key;
        this.requester = requester;
    }

    public List<WeatherReport> getForecast(Location location, int days) throws IOException {
        if(days < 0) return null;
        String query = URL +
                "?lat=" + location.lat +
                "&lon=" + location.lon +
                "&units=" + UNITS +
                "&exclude=" + EXCLUDE +
                "&appid=" + key;

        String result = requester.get(new URL(query));

        List<WeatherReport> ret = new ArrayList<>();
        try {

            JSONObject forecast = (JSONObject) new JSONTokener(result).nextValue();
            JSONArray daily = forecast.getJSONArray("daily");

            int i = 0;
            while (i < daily.length() && i < days)
            {
                ret.add(json2WeatherReport(daily.getJSONObject(i)));
                i++;
            }
        } catch (JSONException e) {
            throw new IOException(e);
        }
        return ret;
    }

    private WeatherReport json2WeatherReport(JSONObject day) throws JSONException, MalformedURLException {
        return new WeatherReport(
                day.getLong("dt"),
                day.getJSONObject("temp").getDouble("min"),
                day.getJSONObject("temp").getDouble("max"),
                day.getDouble("humidity"),
                day.getDouble("wind_speed"),
                day.getDouble("pop"),
                new URL(ICON_URL +
                        day.getJSONArray("weather").getJSONObject(0).getString("icon")
                        + ".png")

        );
    }
}
