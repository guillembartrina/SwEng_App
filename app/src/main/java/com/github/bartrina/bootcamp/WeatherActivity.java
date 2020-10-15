package com.github.bartrina.bootcamp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.bartrina.bootcamp.data.HTTPRequester;
import com.github.bartrina.bootcamp.domain.*;
import com.github.bartrina.bootcamp.types.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WeatherActivity extends AppCompatActivity {

    @Inject
    public WeatherProvider weatherer;
    @Inject
    public LocationProvider locator;
    @Inject
    public GeocodingProvider geocoder;
    @Inject
    public HTTPRequester requester;

    private EditText address;
    private Switch current;
    private TableLayout table;
    private Button search;
    private TextView dispaddress;
    private TextView dispcoords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        address = findViewById(R.id.weather_etext_address);
        current = findViewById(R.id.weather_sw_current);
        table = findViewById(R.id.weather_tl_table);
        search = findViewById(R.id.weather_butt_search);
        dispaddress = findViewById(R.id.weather_ptext_address);
        dispcoords = findViewById(R.id.weather_ptext_coords);

        if(!Common.checkAndGetPermissions(this, new String[] {Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION}))
        {
            finish();
        }

        /*
        Context context = getApplicationContext();
        String key = getResources().getString(R.string.OWM_key);
        weatherer = new OpenWeatherMap(key);
        locator = new SimpleLocator(context);
        geocoder = new SimpleGeocoder(context);
        */
    }

    public void searchClicked(View view) {
        Location location = null;
        List<WeatherReport> forecast = null;

        try {
            if(current.isChecked()) {
                location = locator.getCurrentLocation();

                dispaddress.setText(geocoder.location2Address(location).toString());
            } else {
                String addr = address.getText().toString();
                if(addr.isEmpty()) {
                    address.setError("Empty address");
                    return;
                } else {
                    location = geocoder.address2Location(new Address(Collections.singletonList(addr)));
                }

                dispaddress.setText(addr);
            }
            dispcoords.setText((int)location.lat + " lat | " + (int)location.lon + " lon");
            forecast = weatherer.getForecast(location, 7);
        } catch (IOException e) {
            search.setError("Something went wrong!");
            return;
        }



        table.removeAllViews();

        String[] columns = { "Date", "T.min", "T.max", "Hum.", "Prec.", "Wind", "" };
        TableRow header = new TableRow(this);
        for(String col : columns) {
            TextView c = new TextView(this);
            c.setText(col);
            c.setPadding(30, 0, 0, 0);
            c.setTextSize(14.f);
            header.addView(c);
        }
        header.setBackgroundColor(Color.GRAY);
        table.addView(header);

        String[] magnitude = { "", "ºC", "ºC", "%", "%", "m/s", "" };
        TableRow header2 = new TableRow(this);
        for(String mag : magnitude) {
            TextView c = new TextView(this);
            c.setText(mag);
            c.setPadding(30, 0, 0, 0);
            c.setTextSize(14.f);
            header2.addView(c);
        }
        header2.setBackgroundColor(Color.GRAY);
        table.addView(header2);

        for(int i = 0; i < forecast.size(); i++)
        {
            TableRow row = new TableRow(this);
            TextView day = new TextView(this);
            Date date = new Date(forecast.get(i).day*1000);
            DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d");
            day.setText(dateFormat.format(date));
            day.setTextSize(14.f);
            day.setPadding(30, 0, 0, 0);
            TextView temp_min = new TextView(this);
            temp_min.setText(Double.toString(Math.round(forecast.get(i).temperature_min * 10.0) / 10.0));
            temp_min.setTextSize(14.f);
            temp_min.setPadding(30, 0, 0, 0);
            TextView temp_max = new TextView(this);
            temp_max.setText(Double.toString(Math.round(forecast.get(i).temperature_max * 10.0) / 10.0));
            temp_max.setTextSize(14.f);
            temp_max.setPadding(30, 0, 0, 0);
            TextView humidity = new TextView(this);
            humidity.setText(Integer.toString((int)forecast.get(i).humidity));
            humidity.setTextSize(14.f);
            humidity.setPadding(30, 0, 0, 0);
            TextView pop = new TextView(this);
            pop.setText(Integer.toString((int)(forecast.get(i).precipitation * 100.0)));
            pop.setTextSize(14.f);
            pop.setPadding(30, 0, 0, 0);
            TextView wind_speed = new TextView(this);
            wind_speed.setText(Double.toString(forecast.get(i).wind));
            wind_speed.setTextSize(14.f);
            wind_speed.setPadding(30, 0, 0, 0);

            ImageView icon = new ImageView(this);
            Bitmap bmp = null;
            try {
                bmp = requester.getBitmap(forecast.get(i).icon);
            } catch (IOException e) {
                search.setError("Something went wrong!");
                return;
            }
            icon.setImageBitmap(bmp);
            icon.setPadding(30, 0, 10, 0);
            icon.setScaleX(1.5f);
            icon.setScaleY(1.5f);

            row.addView(day);
            row.addView(temp_min);
            row.addView(temp_max);
            row.addView(humidity);
            row.addView(pop);
            row.addView(wind_speed);
            row.addView(icon);
            table.addView(row);
        }
    }

    public void currentClicked(View view) {
        address.setEnabled(!current.isChecked());
    }
}