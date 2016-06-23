package com.michalkarmelita.marvelcomics.presenter.comics;

import com.michalkarmelita.marvelcomics.view.comics.ComicsView;

public interface ComicsPresenter {

    void detachView();

    void attachView(ComicsView view);

    void setBudget(String budget);

    String getCurrentBudget();

}
