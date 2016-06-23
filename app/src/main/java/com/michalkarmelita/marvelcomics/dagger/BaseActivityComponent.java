package com.michalkarmelita.marvelcomics.dagger;


import com.michalkarmelita.marvelcomics.dagger.daggerscopes.ActivityScope;
import com.michalkarmelita.marvelcomics.presenter.details.DetailsPresenter;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = {
                ActivityModule.class
        }
)
public interface BaseActivityComponent {

}