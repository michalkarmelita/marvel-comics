package com.michalkarmelita.marvelcomics.presenter.details;

import com.michalkarmelita.marvelcomics.view.details.ComicsDetailsView;

public interface DetailsPresenter {

    void onDestroy();

    void onCreate(ComicsDetailsView view);

}
