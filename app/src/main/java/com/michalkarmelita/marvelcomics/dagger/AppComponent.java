package com.michalkarmelita.marvelcomics.dagger;

import com.michalkarmelita.marvelcomics.App;
import com.michalkarmelita.marvelcomics.NetworkModule;
import com.michalkarmelita.marvelcomics.api.MarvelApiService;
import com.michalkarmelita.marvelcomics.dagger.daggerqualifiers.NetworkScheduler;
import com.michalkarmelita.marvelcomics.dagger.daggerqualifiers.UiScheduler;

import javax.inject.Singleton;

import dagger.Component;
import rx.Scheduler;

@Singleton
@Component(
        modules = {
                AppModule.class,
                NetworkModule.class
        }
)
public interface AppComponent {

    void inject(App app);

    MarvelApiService getApiService();

    @NetworkScheduler
    Scheduler getNetworkScheduler();

    @UiScheduler
    Scheduler getUiScheduler();

}
