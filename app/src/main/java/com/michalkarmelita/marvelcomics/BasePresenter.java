package com.michalkarmelita.marvelcomics;

import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<T> {

    protected T view;
    protected CompositeSubscription subscription;

    public void attachView(T view) {
        this.view = view;
        subscribe();
    }

    public void detachView() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    protected abstract void subscribe();
}
