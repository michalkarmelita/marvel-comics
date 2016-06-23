package com.michalkarmelita.marvelcomics.view.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.michalkarmelita.marvelcomics.presenter.comics.ComicsPresenter;
import com.michalkarmelita.marvelcomics.presenter.details.DetailsPresenter;
import com.michalkarmelita.marvelcomics.retain.RetainFragmentManager;

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

    public static Intent newInstance(Context context, int itemId) {
        return new Intent(context, ComicDetailsActivity.class)
                .putExtra(ITEM_ID, String.valueOf(itemId));
    }

    @Inject
    @ForActivity
    Context context;

    private DetailsPresenter presenter;
    protected ComicsDetailsActivityComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (savedInstanceState == null) {
            presenter = component.presenter();
            RetainFragmentManager.setObject(DetailsPresenter.class.getCanonicalName(), getSupportFragmentManager(), presenter);
        } else {
            presenter = RetainFragmentManager.getObject(DetailsPresenter.class.getCanonicalName(), getSupportFragmentManager());
        }

        presenter.attachView(this);

    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @NonNull
    @Override
    protected BaseActivityComponent createActivityComponent() {
        component = DaggerComicsDetailsActivityComponent.builder()
                .appComponent(App.getAppComponent(getApplication()))
                .activityModule(new ActivityModule(this))
                .comicsDetailsActivityModule(new ComicsDetailsActivityModule(getIntent().getStringExtra(ITEM_ID)))
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
