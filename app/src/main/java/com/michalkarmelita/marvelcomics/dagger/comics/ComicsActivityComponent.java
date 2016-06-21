package com.michalkarmelita.marvelcomics.dagger.comics;

import com.michalkarmelita.marvelcomics.dagger.AppComponent;
import com.michalkarmelita.marvelcomics.dagger.BaseActivityComponent;
import com.michalkarmelita.marvelcomics.dagger.daggerscopes.ActivityScope;
import com.michalkarmelita.marvelcomics.view.comics.ComicsActivity;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = ComicsActivityModule.class
)
public interface ComicsActivityComponent extends BaseActivityComponent {

    void inject(ComicsActivity activity);

}
