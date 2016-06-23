package com.michalkarmelita.marvelcomics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.michalkarmelita.marvelcomics.dagger.BaseActivityComponent;

public abstract class BaseActivity extends AppCompatActivity {

    protected BaseActivityComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        component = createActivityComponent();
        super.onCreate(savedInstanceState);
    }

    @NonNull
    protected abstract BaseActivityComponent createActivityComponent();

    @NonNull
    public BaseActivityComponent getComponent() {
        return component;
    }

}
