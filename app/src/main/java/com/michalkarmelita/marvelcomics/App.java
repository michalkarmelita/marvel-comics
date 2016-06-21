package com.michalkarmelita.marvelcomics;

import android.app.Application;

import com.michalkarmelita.marvelcomics.dagger.AppComponent;
import com.michalkarmelita.marvelcomics.dagger.AppModule;
import com.michalkarmelita.marvelcomics.dagger.DaggerAppComponent;

public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        setupDaggerGraph();
    }

    private void setupDaggerGraph() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }

    public static AppComponent getAppComponent(Application app) {
        return ((App) app).appComponent;
    }

}
