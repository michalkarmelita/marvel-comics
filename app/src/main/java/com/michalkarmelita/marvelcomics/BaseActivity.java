package com.michalkarmelita.marvelcomics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.michalkarmelita.marvelcomics.dagger.BaseActivityComponent;

public abstract class BaseActivity extends AppCompatActivity {

    private BaseActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityComponent = createActivityComponent();
        super.onCreate(savedInstanceState);
    }

    @NonNull
    protected abstract BaseActivityComponent createActivityComponent();

    @NonNull
    public BaseActivityComponent getActivityComponent() {
        return activityComponent;
    }

}
