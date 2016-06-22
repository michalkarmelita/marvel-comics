package com.michalkarmelita.marvelcomics.presenter.comics;

import com.michalkarmelita.marvelcomics.view.comics.ComicsView;

public interface ComicsPresenter {

    void onDestroy();

    void onCreate(ComicsView view);

    void setBudget(String budget);

}
