package com.github.bartrina.bootcamp;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.GrantPermissionRule;

import com.github.bartrina.bootcamp.data.HTTPRequester;
import com.github.bartrina.bootcamp.data.HTTPRequesterModule;
import com.github.bartrina.bootcamp.domain.GeocodingProvider;
import com.github.bartrina.bootcamp.domain.GeocodingProviderModule;
import com.github.bartrina.bootcamp.domain.LocationProvider;
import com.github.bartrina.bootcamp.domain.LocationProviderModule;
import com.github.bartrina.bootcamp.domain.WeatherProvider;
import com.github.bartrina.bootcamp.domain.WeatherProviderModule;
import com.github.bartrina.bootcamp.types.Address;
import com.github.bartrina.bootcamp.types.Location;
import com.github.bartrina.bootcamp.types.WeatherReport;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dagger.hilt.android.testing.BindValue;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;
import dagger.hilt.android.testing.UninstallModules;

import static org.hamcrest.Matchers.containsString;

@HiltAndroidTest
@UninstallModules({WeatherProviderModule.class, LocationProviderModule.class, GeocodingProviderModule.class, HTTPRequesterModule.class})
public class WeatherActivityTest {

    @Rule
    public RuleChain rule = RuleChain.outerRule(new HiltAndroidRule(this))
            .around(new ActivityScenarioRule<>(WeatherActivity.class));

    @Rule
    public GrantPermissionRule runtimePermission1 = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public GrantPermissionRule runtimePermission2 = GrantPermissionRule.grant(Manifest.permission.ACCESS_COARSE_LOCATION);

    @Rule
    public GrantPermissionRule runtimePermission3 = GrantPermissionRule.grant(Manifest.permission.INTERNET);

    @BindValue
    public WeatherProvider weatherer = Mockito.mock(WeatherProvider.class);

    @BindValue
    public LocationProvider locator = Mockito.mock(LocationProvider.class);

    @BindValue
    public GeocodingProvider geocoder = Mockito.mock(GeocodingProvider.class);

    @BindValue
    public HTTPRequester requester = Mockito.mock(HTTPRequester.class);

    private static Location fakeLocation1 = new Location(141.45, 99.72);
    private static Location fakeLocation2 = new Location(467.04, 2.11);
    private static String address1 = "New York";
    private static String address2 = "Sydney";

    private static URL fakeURL;

    static {
        try {
            fakeURL = new URL("https://www.google.com/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void initWeatherer() throws IOException {

        List<WeatherReport> reports1 = new ArrayList<WeatherReport>();
        reports1.add(new WeatherReport(0, 0.0, 20.0, 0.0, 0.0, 0.8, fakeURL));
        reports1.add(new WeatherReport(1, 10.0, 30.0, 0.0, 40.0, 0.2, fakeURL));

        List<WeatherReport> reports2 = new ArrayList<WeatherReport>();
        reports2.add(new WeatherReport(0, 0.0, 20.0, 0.0, 0.0, 0.8, fakeURL));
        reports2.add(new WeatherReport(1, 10.0, 30.0, 0.0, 40.0, 0.2, fakeURL));
        reports2.add(new WeatherReport(2, 20.0, 30.0, 0.5, 90.0, 0.0, fakeURL));

        Mockito.when(weatherer.getForecast(fakeLocation1, 0)).thenReturn(new ArrayList<>());
        Mockito.when(weatherer.getForecast(fakeLocation1, 7)).thenReturn(reports1);

        Mockito.when(weatherer.getForecast(fakeLocation2, 0)).thenReturn(new ArrayList<>());
        Mockito.when(weatherer.getForecast(fakeLocation2, 7)).thenReturn(reports2);

        Mockito.when(requester.getBitmap(fakeURL)).thenReturn(Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888));
    }

    @Test
    public void currentLocationGivesOK() throws IOException {

        Mockito.when(locator.getCurrentLocation()).thenReturn(fakeLocation1);
        Mockito.when(geocoder.location2Address(fakeLocation1)).thenReturn(new Address(Collections.singletonList(address1)));

        ViewInteraction current = Espresso.onView(ViewMatchers.withId(R.id.weather_sw_current));
        current.perform(ViewActions.closeSoftKeyboard());
        current.perform(ViewActions.click());
        ViewInteraction search = Espresso.onView(ViewMatchers.withId(R.id.weather_butt_search));
        search.perform(ViewActions.click());

        ViewInteraction outaddress = Espresso.onView(ViewMatchers.withId(R.id.weather_ptext_address));
        outaddress.check(ViewAssertions.matches(ViewMatchers.withText(new Address(Collections.singletonList(address1)).toString())));

        ViewInteraction outcoords = Espresso.onView(ViewMatchers.withId(R.id.weather_ptext_coords));
        outcoords.check(ViewAssertions.matches(ViewMatchers.withText(containsString(Integer.toString((int)fakeLocation1.lat)))));
        outcoords.check(ViewAssertions.matches(ViewMatchers.withText(containsString(Integer.toString((int)fakeLocation1.lon)))));

        ViewInteraction outtable = Espresso.onView(ViewMatchers.withId(R.id.weather_tl_table));
        outtable.check(ViewAssertions.matches(ViewMatchers.hasChildCount(4)));
    }

    @Test
    public void enteredLocationGivesOK() throws IOException {

        Mockito.when(geocoder.address2Location(Mockito.eq(new Address(Collections.singletonList(address2))))).thenReturn(fakeLocation2);

        ViewInteraction address = Espresso.onView(ViewMatchers.withId(R.id.weather_etext_address));
        address.perform(ViewActions.click());
        address.perform(ViewActions.typeText(address2));
        ViewInteraction search = Espresso.onView(ViewMatchers.withId(R.id.weather_butt_search));
        search.perform(ViewActions.click());

        ViewInteraction outaddress = Espresso.onView(ViewMatchers.withId(R.id.weather_ptext_address));
        outaddress.check(ViewAssertions.matches(ViewMatchers.withText(address2)));

        ViewInteraction outcoords = Espresso.onView(ViewMatchers.withId(R.id.weather_ptext_coords));
        outcoords.check(ViewAssertions.matches(ViewMatchers.withText(containsString(Integer.toString((int)fakeLocation2.lat)))));
        outcoords.check(ViewAssertions.matches(ViewMatchers.withText(containsString(Integer.toString((int)fakeLocation2.lon)))));

        ViewInteraction outtable = Espresso.onView(ViewMatchers.withId(R.id.weather_tl_table));
        outtable.check(ViewAssertions.matches(ViewMatchers.hasChildCount(5)));


    }
}
