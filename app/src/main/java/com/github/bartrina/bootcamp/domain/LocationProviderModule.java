package com.github.bartrina.bootcamp.domain;

import android.content.Context;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Module
@InstallIn(ApplicationComponent.class)
public abstract class LocationProviderModule {

    @Binds
    public abstract LocationProvider bindLocationProvider(SimpleLocator locator);

}
