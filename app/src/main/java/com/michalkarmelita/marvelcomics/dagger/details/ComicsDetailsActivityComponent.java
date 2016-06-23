package com.michalkarmelita.marvelcomics.dagger.details;

import com.michalkarmelita.marvelcomics.dagger.ActivityModule;
import com.michalkarmelita.marvelcomics.dagger.AppComponent;
import com.michalkarmelita.marvelcomics.dagger.BaseActivityComponent;
import com.michalkarmelita.marvelcomics.dagger.daggerscopes.ActivityScope;
import com.michalkarmelita.marvelcomics.presenter.comics.ComicsPresenter;
import com.michalkarmelita.marvelcomics.presenter.details.DetailsPresenter;
import com.michalkarmelita.marvelcomics.view.details.ComicDetailsActivity;

import dagger.Component;

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = {
                ActivityModule.class,
                ComicsDetailsActivityModule.class
        }
)
public interface ComicsDetailsActivityComponent extends BaseActivityComponent {

        void inject(ComicDetailsActivity activity);

        DetailsPresenter presenter();
}
