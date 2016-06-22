package com.michalkarmelita.marvelcomics.dagger.comics;

import android.content.Context;
import android.view.LayoutInflater;

import com.michalkarmelita.marvelcomics.api.MarvelApiService;
import com.michalkarmelita.marvelcomics.dagger.daggerqualifiers.ForActivity;
import com.michalkarmelita.marvelcomics.dagger.daggerqualifiers.NetworkScheduler;
import com.michalkarmelita.marvelcomics.dagger.daggerqualifiers.UiScheduler;
import com.michalkarmelita.marvelcomics.dagger.daggerscopes.ActivityScope;
import com.michalkarmelita.marvelcomics.presenter.comics.ComicsPresenter;
import com.michalkarmelita.marvelcomics.presenter.comics.ComicsPresenterImpl;
import com.michalkarmelita.marvelcomics.view.comics.ComicsActivity;
import com.michalkarmelita.marvelcomics.view.comics.ComicsView;
import com.michalkarmelita.marvelcomics.view.comics.adapter.ComicsAdapter;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class ComicsActivityModule {

    private final ComicsActivity view;

    public ComicsActivityModule(ComicsActivity view) {
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

    @Provides
    @ActivityScope
    ComicsAdapter provideComicsAdapter(@ForActivity Context context, LayoutInflater inflater) {
        return new ComicsAdapter(context, inflater, view);
    }

}
