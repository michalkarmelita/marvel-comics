package com.michalkarmelita.marvelcomics.utils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxUtils {

    public static <T> Func1<List<T>, Boolean> notEmptyList() {
        return new Func1<List<T>, Boolean>() {
            @Override
            public Boolean call(List<T> list) {
                return !list.isEmpty();
            }
        };
    }

    public static <T> Observable.Transformer<T, T> applyNetworkSchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable.Transformer<T, T> applyComputationSchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Func0<List<T>> createList() {
        return new Func0<List<T>>() {
            @Override
            public List<T> call() {
                return new ArrayList<>();
            }
        };
    }

    public static <T> Action2<List<T>, T> addItemToList() {
        return new Action2<List<T>, T>() {
            @Override
            public void call(List<T> collectedItems, T item) {
                collectedItems.add(item);
            }
        };
    }

}
