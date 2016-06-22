package com.michalkarmelita.marvelcomics.view.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.michalkarmelita.marvelcomics.App;
import com.michalkarmelita.marvelcomics.BaseActivity;
import com.michalkarmelita.marvelcomics.R;
import com.michalkarmelita.marvelcomics.dagger.ActivityModule;
import com.michalkarmelita.marvelcomics.dagger.BaseActivityComponent;
import com.michalkarmelita.marvelcomics.dagger.daggerqualifiers.ForActivity;
import com.michalkarmelita.marvelcomics.dagger.details.ComicsDetailsActivityComponent;
import com.michalkarmelita.marvelcomics.dagger.details.ComicsDetailsActivityModule;
import com.michalkarmelita.marvelcomics.dagger.details.DaggerComicsDetailsActivityComponent;
import com.michalkarmelita.marvelcomics.presenter.details.DetailsPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ComicDetailsActivity extends BaseActivity implements ComicsDetailsView {

    private static final String ITEM_ID = "id";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.MyAppbar)
    AppBarLayout MyAppbar;
    @Bind(R.id.main_content)
    CoordinatorLayout mainContent;
    @Bind(R.id.toolbar_bg)
    ImageView toolbarBg;
    @Bind(R.id.details_description)
    TextView detailsDescription;
    @Bind(R.id.details_pages)
    TextView detailsPages;
    @Bind(R.id.details_price)
    TextView detailsPrice;
    @Bind(R.id.details_creators)
    TextView detailsCreators;

    @Inject
    @ForActivity
    Context context;

    public static Intent newInstance(Context context, int itemId) {
        return new Intent(context, ComicDetailsActivity.class)
                .putExtra(ITEM_ID, String.valueOf(itemId));
    }

    @Inject
    DetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        ButterKnife.bind(this);
        presenter.onCreate(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @NonNull
    @Override
    protected BaseActivityComponent createActivityComponent() {
        final ComicsDetailsActivityComponent component = DaggerComicsDetailsActivityComponent.builder()
                .appComponent(App.getAppComponent(getApplication()))
                .activityModule(new ActivityModule(this))
                .comicsDetailsActivityModule(new ComicsDetailsActivityModule(this, getIntent().getStringExtra(ITEM_ID)))
                .build();
        component.inject(this);
        return component;
    }

    @Override
    public void setImage(String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .fitCenter()
                .into(toolbarBg);
    }

    @Override
    public void setTitle(String title) {
        collapsingToolbar.setTitle(title);
    }

    @Override
    public void setCreators(String creators) {
        detailsCreators.setText(creators);
    }

    @Override
    public void setDescription(String description) {
        detailsDescription.setText(description);
    }

    @Override
    public void setPages(String pages) {
        detailsPages.setText(pages);
    }

    @Override
    public void setPrice(String price) {
        detailsPrice.setText(price);
    }
}
