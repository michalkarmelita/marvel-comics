package com.michalkarmelita.marvelcomics.view.comics.adapter.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.michalkarmelita.marvelcomics.view.comics.adapter.model.BaseAdapterItem;

public abstract class ComicsAdapterBaseViewHolder extends RecyclerView.ViewHolder {

    public ComicsAdapterBaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(BaseAdapterItem baseAdapterItem);
}
