package com.michalkarmelita.marvelcomics.dagger;

import android.content.Context;

import com.michalkarmelita.marvelcomics.App;
import com.michalkarmelita.marvelcomics.dagger.daggerqualifiers.ForApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @ForApplication
    Context provideApplicationContext() {
        return app;
    }

}
