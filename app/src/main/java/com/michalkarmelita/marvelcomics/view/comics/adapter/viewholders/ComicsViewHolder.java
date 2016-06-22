package com.michalkarmelita.marvelcomics.view.comics.adapter.viewholders;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.michalkarmelita.marvelcomics.R;
import com.michalkarmelita.marvelcomics.view.comics.adapter.ComicsAdapter;
import com.michalkarmelita.marvelcomics.view.comics.adapter.model.BaseAdapterItem;
import com.michalkarmelita.marvelcomics.view.comics.adapter.model.ComicAdapterItem;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ComicsViewHolder extends ComicsAdapterBaseViewHolder {

    private final Context context;
    private final ComicsAdapter.ComicClickListener clickListener;

    @Bind(R.id.comic_icon)
    ImageView comicIcon;
    @Bind(R.id.comic_title)
    TextView comicTitle;
    @Bind(R.id.card_view)
    CardView cardView;

    public ComicsViewHolder(View itemView, Context context, ComicsAdapter.ComicClickListener clickListener) {
        super(itemView);
        this.context = context;
        this.clickListener = clickListener;
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(BaseAdapterItem baseAdapterItem) {
        final ComicAdapterItem item = (ComicAdapterItem) baseAdapterItem;

        Glide.with(context)
                .load(item.getIconUrl())
                .fitCenter()
                .into(comicIcon);

        comicTitle.setText(item.getTitle());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onComicClick(item.getId());
            }
        });

    }

}
