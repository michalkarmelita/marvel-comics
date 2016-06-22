package com.michalkarmelita.marvelcomics;

import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter {

    protected final CompositeSubscription subscription;

    public BasePresenter() {
        this.subscription = new CompositeSubscription();
    }

    public void onDestroy() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
