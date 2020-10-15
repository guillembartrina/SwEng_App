package com.github.bartrina.bootcamp.data;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public abstract class HTTPRequesterModule {

    @Binds
    public abstract HTTPRequester bindHTTPRequester(SimpleHTTPRequester requester);

}
