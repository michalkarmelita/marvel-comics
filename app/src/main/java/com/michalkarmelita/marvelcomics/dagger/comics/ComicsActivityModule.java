package com.michalkarmelita.marvelcomics.dagger.comics;

import com.michalkarmelita.marvelcomics.api.MarvelApiService;
import com.michalkarmelita.marvelcomics.dagger.daggerqualifiers.NetworkScheduler;
import com.michalkarmelita.marvelcomics.dagger.daggerqualifiers.UiScheduler;
import com.michalkarmelita.marvelcomics.dagger.daggerscopes.ActivityScope;
import com.michalkarmelita.marvelcomics.presenter.comics.ComicsPresenter;
import com.michalkarmelita.marvelcomics.presenter.comics.ComicsPresenterImpl;
import com.michalkarmelita.marvelcomics.view.comics.ComicsView;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class ComicsActivityModule {

    private final ComicsView view;

    public ComicsActivityModule(ComicsView view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    ComicsView provideComicsView() {
        return view;
    }

    @Provides
    @ActivityScope
    ComicsPresenter provideComicsPresenter(@NetworkScheduler Scheduler networkScheduler, @UiScheduler Scheduler uiScheduler, MarvelApiService apiService) {
        return new ComicsPresenterImpl(networkScheduler, uiScheduler, apiService);
    }

}
