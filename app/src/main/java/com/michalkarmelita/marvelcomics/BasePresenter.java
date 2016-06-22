package com.michalkarmelita.marvelcomics;

import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<T> {

    protected T view;
    protected final CompositeSubscription subscription;

    public BasePresenter() {
        this.subscription = new CompositeSubscription();
    }

    public void onCreate(T view) {
        this.view = view;
        subscribe();
    }

    public void onDestroy() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    protected abstract void subscribe();
}
