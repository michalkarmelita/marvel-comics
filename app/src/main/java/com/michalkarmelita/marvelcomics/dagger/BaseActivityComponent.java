package com.michalkarmelita.marvelcomics.dagger;


import com.michalkarmelita.marvelcomics.dagger.daggerscopes.ActivityScope;

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
