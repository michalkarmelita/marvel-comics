package com.michalkarmelita.marvelcomics.view.comics;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.michalkarmelita.marvelcomics.App;
import com.michalkarmelita.marvelcomics.BaseActivity;
import com.michalkarmelita.marvelcomics.R;
import com.michalkarmelita.marvelcomics.dagger.ActivityModule;
import com.michalkarmelita.marvelcomics.dagger.BaseActivityComponent;
import com.michalkarmelita.marvelcomics.dagger.comics.ComicsActivityComponent;
import com.michalkarmelita.marvelcomics.dagger.comics.ComicsActivityModule;
import com.michalkarmelita.marvelcomics.dagger.comics.DaggerComicsActivityComponent;
import com.michalkarmelita.marvelcomics.dagger.daggerqualifiers.ForActivity;
import com.michalkarmelita.marvelcomics.presenter.comics.ComicsPresenter;
import com.michalkarmelita.marvelcomics.utils.DialogUtils;
import com.michalkarmelita.marvelcomics.view.comics.adapter.ComicsAdapter;
import com.michalkarmelita.marvelcomics.view.comics.adapter.model.BaseAdapterItem;
import com.michalkarmelita.marvelcomics.view.comics.adapter.model.HeaderItem;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ComicsActivity extends BaseActivity implements ComicsView, ComicsAdapter.ComicClickListener, DialogUtils.BudgetListener {

    @Bind(R.id.comics_recycler_view)
    RecyclerView comicsRecyclerView;

    @Inject
    @ForActivity
    Context context;
    @Inject
    ComicsPresenter presenter;
    @Inject
    ComicsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics);
        ButterKnife.bind(this);

        presenter.onCreate(this);

        comicsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        comicsRecyclerView.setAdapter(adapter);

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
                .activityModule(new ActivityModule(this))
                .comicsActivityModule(new ComicsActivityModule(this))
                .build();
        component.inject(this);
        return component;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comics_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter) {
            DialogUtils.showInfoDialog(context, this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setfilteredComicsList(List<BaseAdapterItem> adapterItems) {
        adapter.setItems(adapterItems);
    }

    @Override
    public void updateComicsList(List<BaseAdapterItem> adapterItems) {
        adapter.addItems(adapterItems);
    }

    @Override
    public void setPages(HeaderItem header) {
        adapter.addHeader(header);
    }

    @Override
    public void onComicClick(int itemId) {

    }

    @Override
    public void onSetBudget(String budget) {
        presenter.setBudget(budget);
    }
}
