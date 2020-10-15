package com.github.bartrina.bootcamp.domain;

import android.content.Context;

import androidx.core.graphics.drawable.IconCompat;

import com.github.bartrina.bootcamp.R;
import com.github.bartrina.bootcamp.data.HTTPRequester;
import com.github.bartrina.bootcamp.data.SimpleHTTPRequester;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Module
@InstallIn(ApplicationComponent.class)
public abstract class WeatherProviderModule {

    @Binds
    public abstract WeatherProvider bindWeatherProvider(OpenWeatherMap weatherer);

    @Provides
    public static String provideKey(@ApplicationContext Context context) {
        return context.getResources().getString(R.string.OWM_key);
    }
}
