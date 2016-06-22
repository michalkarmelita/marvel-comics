package com.michalkarmelita.marvelcomics.presenter.comics;

import com.michalkarmelita.marvelcomics.BasePresenter;
import com.michalkarmelita.marvelcomics.api.MarvelApiService;
import com.michalkarmelita.marvelcomics.api.model.ComicsResponse;

import rx.Scheduler;
import rx.functions.Action1;

public class ComicsPresenterImpl extends BasePresenter implements ComicsPresenter {

    public ComicsPresenterImpl(Scheduler networkScheduler, Scheduler uiScheduler, MarvelApiService apiService) {

        subscription.add(apiService.getComics()
                .subscribeOn(networkScheduler)
                .observeOn(uiScheduler)
                .subscribe(new Action1<ComicsResponse>() {
            @Override
            public void call(ComicsResponse comicsResponse) {

            }
        }));

    }
}
