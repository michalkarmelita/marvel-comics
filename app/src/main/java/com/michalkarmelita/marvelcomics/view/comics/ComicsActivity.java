package com.michalkarmelita.marvelcomics.view.comics;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.michalkarmelita.marvelcomics.App;
import com.michalkarmelita.marvelcomics.BaseActivity;
import com.michalkarmelita.marvelcomics.R;
import com.michalkarmelita.marvelcomics.dagger.BaseActivityComponent;
import com.michalkarmelita.marvelcomics.dagger.comics.ComicsActivityComponent;
import com.michalkarmelita.marvelcomics.dagger.comics.DaggerComicsActivityComponent;

public class ComicsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics);
    }

    @NonNull
    @Override
    protected BaseActivityComponent createActivityComponent() {
        final ComicsActivityComponent component = DaggerComicsActivityComponent.builder()
                .appComponent(App.getAppComponent(getApplication()))
                .build();
        component.inject(this);
        return component;
    }
}
