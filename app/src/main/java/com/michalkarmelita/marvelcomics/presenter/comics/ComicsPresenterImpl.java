package com.michalkarmelita.marvelcomics.presenter.comics;

import com.michalkarmelita.marvelcomics.BasePresenter;
import com.michalkarmelita.marvelcomics.api.MarvelApiService;
import com.michalkarmelita.marvelcomics.api.model.ComicsResponse;
import com.michalkarmelita.marvelcomics.api.model.Result;
import com.michalkarmelita.marvelcomics.utils.ComicImageUrlHelper;
import com.michalkarmelita.marvelcomics.utils.ComicPriceComparator;
import com.michalkarmelita.marvelcomics.utils.RxUtils;
import com.michalkarmelita.marvelcomics.view.comics.ComicsView;
import com.michalkarmelita.marvelcomics.view.comics.adapter.model.BaseAdapterItem;
import com.michalkarmelita.marvelcomics.view.comics.adapter.model.ComicAdapterItem;
import com.michalkarmelita.marvelcomics.view.comics.adapter.model.HeaderItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

public class ComicsPresenterImpl extends BasePresenter<ComicsView> implements ComicsPresenter {

    private static final int OFFSET = 20;
    private static final int LIMIT = 20;

    private final Observable<HeaderItem> pagesObservable;
    private final Observable<List<BaseAdapterItem>> comicsItemsObservable;
    private final BehaviorSubject<String> budgetSubject = BehaviorSubject.create();
    private final Observable<List<BaseAdapterItem>> filteredItems;

    public ComicsPresenterImpl(final Scheduler networkScheduler, Scheduler uiScheduler, final MarvelApiService apiService) {

        //I decided to use this "hack" is used to get exactly 100 items splitted to 5 API calls
        //in order to present part of the data faster. Normally I would implement load more on scroll event
        //something like here https://github.com/michalkarmelita/twitter-hashtags-tracker/blob/master/app/src/main/java/com/michalkarmelita/hashtagtracker/model/tweets/TweetsDataProvider.java
        final Observable<Integer> nextObservable = Observable.just(0, 1, 2, 3, 4);

        final Observable<List<Result>> apiResultsObservable = nextObservable
                .flatMap(new Func1<Integer, Observable<ComicsResponse>>() {
                    @Override
                    public Observable<ComicsResponse> call(Integer nextToken) {
                        return apiService.getComics(nextToken * OFFSET, LIMIT);
                    }
                })
                .map(new Func1<ComicsResponse, List<Result>>() {
                    @Override
                    public List<Result> call(ComicsResponse comicsResponse) {
                        return comicsResponse.getData().getResults();
                    }
                })
                .scan(new Func2<List<Result>, List<Result>, List<Result>>() {
                    @Override
                    public List<Result> call(List<Result> results, List<Result> newResults) {
                        results.addAll(newResults);
                        return results;
                    }
                });

        comicsItemsObservable = apiResultsObservable
                .flatMap(new Func1<List<Result>, Observable<List<BaseAdapterItem>>>() {
                    @Override
                    public Observable<List<BaseAdapterItem>> call(List<Result> results) {
                        return Observable.from(results)
                                .map(new Func1<Result, ComicAdapterItem>() {
                                    @Override
                                    public ComicAdapterItem call(Result result) {
                                        return new ComicAdapterItem(
                                                result.getId(),
                                                result.getTitle(),
                                                result.getPageCount(),
                                                result.getPrices().get(0).getPrice(),
                                                ComicImageUrlHelper.getSmallImageUrl(result.getThumbnail().getPath(), result.getThumbnail().getExtension()));
                                    }
                                })
                                .collect(RxUtils.<BaseAdapterItem>createList(),
                                        RxUtils.<BaseAdapterItem>addItemToList());
                    }
                })
                .cacheWithInitialCapacity(1)
                .subscribeOn(networkScheduler)
                .observeOn(uiScheduler);

        filteredItems = budgetSubject
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String budget) {
                        if (budget.equals("")) {
                            return Integer.MAX_VALUE;
                        } else {
                            return Integer.valueOf(budget);
                        }
                    }
                })
                .flatMap(new Func1<Integer, Observable<List<BaseAdapterItem>>>() {
                    @Override
                    public Observable<List<BaseAdapterItem>> call(final Integer budget) {
                        return comicsItemsObservable.map(new Func1<List<BaseAdapterItem>, List<BaseAdapterItem>>() {
                            @Override
                            public List<BaseAdapterItem> call(List<BaseAdapterItem> baseAdapterItems) {
                                int priceSum = 0;
                                Collections.sort(baseAdapterItems, new ComicPriceComparator());

                                List<BaseAdapterItem> filtered = new ArrayList<>();

                                for (BaseAdapterItem baseAdapterItem : baseAdapterItems) {
                                    final ComicAdapterItem item = (ComicAdapterItem) baseAdapterItem;
                                    priceSum += item.getPrice();
                                    if (priceSum < budget) {
                                        filtered.add(item);
                                    } else {
                                        break;
                                    }
                                }

                                return filtered;
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(uiScheduler);

        pagesObservable = Observable.merge(comicsItemsObservable.last(), filteredItems)
                .flatMap(new Func1<List<BaseAdapterItem>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<BaseAdapterItem> baseAdapterItems) {
                        return Observable.from(baseAdapterItems)
                                .map(new Func1<BaseAdapterItem, Integer>() {
                                    @Override
                                    public Integer call(BaseAdapterItem baseAdapterItem) {
                                        final ComicAdapterItem item = (ComicAdapterItem) baseAdapterItem;
                                        return item.getPageCount();
                                    }
                                })
                                .scan(new Func2<Integer, Integer, Integer>() {
                                    @Override
                                    public Integer call(Integer count, Integer newPages) {
                                        return count + newPages;
                                    }
                                })
                                .map(new Func1<Integer, String>() {
                                    @Override
                                    public String call(Integer pages) {
                                        return String.valueOf(pages);
                                    }
                                });
                    }
                })
                .map(new Func1<String, HeaderItem>() {
                    @Override
                    public HeaderItem call(String pages) {
                        return new HeaderItem(pages);
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(uiScheduler);

    }

    @Override
    public void setBudget(String budget) {
        budgetSubject.onNext(budget);
    }

    @Override
    public String getCurrentBudget() {
        if (budgetSubject.hasValue()) {
            return budgetSubject.getValue();
        } else {
            return null;
        }
    }

    @Override
    protected void subscribe() {

        subscription = new CompositeSubscription(
        comicsItemsObservable
                .subscribe(new Action1<List<BaseAdapterItem>>() {
                    @Override
                    public void call(List<BaseAdapterItem> baseAdapterItems) {
                        view.updateComicsList(baseAdapterItems);
                    }
                }),

        pagesObservable
                .subscribe(new Action1<HeaderItem>() {
                    @Override
                    public void call(HeaderItem headerItem) {
                        view.setPages(headerItem);
                    }
                }),

        filteredItems.subscribe(new Action1<List<BaseAdapterItem>>() {
            @Override
            public void call(List<BaseAdapterItem> baseAdapterItems) {
                view.setfilteredComicsList(baseAdapterItems);
            }
        }));
    }
}