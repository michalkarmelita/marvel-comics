package com.michalkarmelita.marvelcomics.dagger.details;

import com.michalkarmelita.marvelcomics.api.MarvelApiService;
import com.michalkarmelita.marvelcomics.dagger.daggerqualifiers.NetworkScheduler;
import com.michalkarmelita.marvelcomics.dagger.daggerqualifiers.UiScheduler;
import com.michalkarmelita.marvelcomics.dagger.daggerscopes.ActivityScope;
import com.michalkarmelita.marvelcomics.presenter.details.DetailsPresenter;
import com.michalkarmelita.marvelcomics.presenter.details.DetailsPresenterImpl;
import com.michalkarmelita.marvelcomics.view.details.ComicsDetailsView;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class ComicsDetailsActivityModule {

    private final ComicsDetailsView view;
    private final String comicId;

    public ComicsDetailsActivityModule(ComicsDetailsView view, String comicId) {
        this.view = view;
        this.comicId = comicId;
    }

    @Provides
    @ActivityScope
    ComicsDetailsView provideComicsView() {
        return view;
    }

    @Provides
    @ActivityScope
    DetailsPresenter provideComicsPresenter(@NetworkScheduler Scheduler networkScheduler, @UiScheduler Scheduler uiScheduler, MarvelApiService apiService) {
        return new DetailsPresenterImpl(networkScheduler, uiScheduler, apiService, comicId);
    }

}
