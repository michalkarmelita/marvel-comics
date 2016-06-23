package com.michalkarmelita.marvelcomics.presenter.details;

import com.michalkarmelita.marvelcomics.view.details.ComicsDetailsView;

public interface DetailsPresenter {

    void detachView();

    void attachView(ComicsDetailsView view);

}
