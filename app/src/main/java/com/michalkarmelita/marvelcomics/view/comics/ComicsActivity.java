package com.michalkarmelita.marvelcomics.view.comics;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.michalkarmelita.marvelcomics.App;
import com.michalkarmelita.marvelcomics.BaseActivity;
import com.michalkarmelita.marvelcomics.R;
import com.michalkarmelita.marvelcomics.dagger.BaseActivityComponent;
import com.michalkarmelita.marvelcomics.dagger.comics.ComicsActivityComponent;
import com.michalkarmelita.marvelcomics.dagger.comics.ComicsActivityModule;
import com.michalkarmelita.marvelcomics.dagger.comics.DaggerComicsActivityComponent;
import com.michalkarmelita.marvelcomics.presenter.comics.ComicsPresenter;

import javax.inject.Inject;

public class ComicsActivity extends BaseActivity implements ComicsView {

    @Inject
    ComicsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @NonNull
    @Override
    protected BaseActivityComponent createActivityComponent() {
        final ComicsActivityComponent component = DaggerComicsActivityComponent.builder()
                .appComponent(App.getAppComponent(getApplication()))
                .comicsActivityModule(new ComicsActivityModule(this))
                .build();
        component.inject(this);
        return component;
    }
}
