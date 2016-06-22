package com.michalkarmelita.marvelcomics.dagger;

import android.content.Context;
import android.view.LayoutInflater;

import com.michalkarmelita.marvelcomics.BaseActivity;
import com.michalkarmelita.marvelcomics.dagger.daggerqualifiers.ForActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ForActivity
    Context provideActivityContext() {
        return activity;
    }

    @Provides
    public LayoutInflater provideLayoutInflater(@ForActivity Context context) {
        return LayoutInflater.from(context);
    }
}
