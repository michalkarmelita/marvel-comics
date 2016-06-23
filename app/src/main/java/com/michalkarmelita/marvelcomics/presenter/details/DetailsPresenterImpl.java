package com.michalkarmelita.marvelcomics.presenter.details;


import android.annotation.SuppressLint;

import com.michalkarmelita.marvelcomics.BasePresenter;
import com.michalkarmelita.marvelcomics.api.MarvelApiService;
import com.michalkarmelita.marvelcomics.api.model.ComicsResponse;
import com.michalkarmelita.marvelcomics.api.model.Image;
import com.michalkarmelita.marvelcomics.api.model.Item;
import com.michalkarmelita.marvelcomics.api.model.Price;
import com.michalkarmelita.marvelcomics.api.model.Result;
import com.michalkarmelita.marvelcomics.api.model.Thumbnail;
import com.michalkarmelita.marvelcomics.utils.ComicImageUrlHelper;
import com.michalkarmelita.marvelcomics.view.details.ComicsDetailsView;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subscriptions.CompositeSubscription;

public class DetailsPresenterImpl extends BasePresenter<ComicsDetailsView> implements DetailsPresenter {

    private final Observable<String> comicImageObservable;
    private final Observable<String> comicTitleObservable;
    private final Observable<String> comicDescriptionObservable;
    private final Observable<String> comicPagesObservable;
    private final Observable<String> comicPriceObservable;
    private final Observable<String> comicCreatorsObservable;

    public DetailsPresenterImpl(Scheduler networkScheduler, Scheduler uiScheduler, MarvelApiService apiService, String comicId) {

        final Observable<Result> comicObservable = apiService.getComic(comicId)
                .subscribeOn(networkScheduler)
                .observeOn(uiScheduler)
                .map(new Func1<ComicsResponse, Result>() {
                    @Override
                    public Result call(ComicsResponse comicsResponse) {
                        return comicsResponse.getData().getResults().get(0);
                    }
                })
                .cache()
                .subscribeOn(networkScheduler);

        comicImageObservable = comicObservable
                .map(new Func1<Result, String>() {
                    @Override
                    public String call(Result result) {
                        if (result.getImages().size() > 0) {
                            final Image image = result.getImages().get(0);
                            return ComicImageUrlHelper.getHeaderImageUrl(image.getPath(), image.getExtension());
                        } else {
                            final Thumbnail thumbnail = result.getThumbnail();
                            return ComicImageUrlHelper.getHeaderImageUrl(thumbnail.getPath(), thumbnail.getExtension());
                        }
                    }
                })
                .observeOn(uiScheduler);

        comicTitleObservable = comicObservable
                .map(new Func1<Result, String>() {
                    @Override
                    public String call(Result result) {
                        return result.getTitle();
                    }
                })
                .observeOn(uiScheduler);

        comicDescriptionObservable = comicObservable
                .map(new Func1<Result, String>() {
                    @Override
                    public String call(Result result) {
                        return result.getDescription();
                    }
                })
                .observeOn(uiScheduler);

        comicPagesObservable = comicObservable
                .map(new Func1<Result, String>() {
                    @Override
                    public String call(Result result) {
                        return String.valueOf(result.getPageCount());
                    }
                })
                .observeOn(uiScheduler);

        comicPriceObservable = comicObservable
                .map(new Func1<Result, String>() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public String call(Result result) {
                        final Price price = result.getPrices().get(0);
                        return String.format("%.2f", price.getPrice());
                    }
                })
                .observeOn(uiScheduler);

        comicCreatorsObservable = comicObservable
                .flatMap(new Func1<Result, Observable<String>>() {
                    @Override
                    public Observable<String> call(Result result) {
                        return Observable.from(result.getCreators().getItems())
                                .map(new Func1<Item, String>() {
                                    @Override
                                    public String call(Item item) {
                                        return item.getName();
                                    }
                                })
                                .scan(new Func2<String, String, String>() {
                                    @Override
                                    public String call(String current, String newName) {
                                        if (current.length() == 0) {
                                            return newName;
                                        } else {
                                            return String.format("%s, %s", current, newName);
                                        }
                                    }
                                });
                    }
                })
                .observeOn(uiScheduler);
    }

    @Override
    protected void subscribe() {
        subscription = new CompositeSubscription(
                comicImageObservable.subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        view.setImage(s);
                    }
                }),

                comicTitleObservable.subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        view.setTitle(s);
                    }
                }),

        comicDescriptionObservable.subscribe(new Action1<String>() {
                    @Override
                    public void call(String description) {
                        view.setDescription(description);
                    }
                }),

        comicPagesObservable.subscribe(new Action1<String>() {
                    @Override
                    public void call(String pages) {
                        view.setPages(pages);
                    }
                }),

        comicCreatorsObservable.subscribe(new Action1<String>() {
                    @Override
                    public void call(String creators) {
                        view.setCreators(creators);
                    }
                }),

        comicPriceObservable.subscribe(new Action1<String>() {
            @Override
            public void call(String price) {
                view.setPrice(price);
            }
        }));
    }
}
