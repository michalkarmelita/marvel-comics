package com.michalkarmelita.marvelcomics.dagger;

import android.content.Context;

import com.michalkarmelita.marvelcomics.App;
import com.michalkarmelita.marvelcomics.dagger.daggerqualifiers.ForApplication;
import com.michalkarmelita.marvelcomics.dagger.daggerqualifiers.NetworkScheduler;
import com.michalkarmelita.marvelcomics.dagger.daggerqualifiers.UiScheduler;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    @Provides
    @NetworkScheduler
    Scheduler provideNetworkScheduler(){
        return Schedulers.io();
    }

    @Provides
    @UiScheduler
    Scheduler provideUiScheduler(){
        return AndroidSchedulers.mainThread();
    }

}
