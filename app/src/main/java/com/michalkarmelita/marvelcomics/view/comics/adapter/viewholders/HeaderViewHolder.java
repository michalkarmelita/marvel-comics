package com.michalkarmelita.marvelcomics.view.comics.adapter.viewholders;

import android.view.View;
import android.widget.TextView;

import com.michalkarmelita.marvelcomics.R;
import com.michalkarmelita.marvelcomics.view.comics.adapter.model.BaseAdapterItem;
import com.michalkarmelita.marvelcomics.view.comics.adapter.model.HeaderItem;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HeaderViewHolder extends ComicsAdapterBaseViewHolder {

    @Bind(R.id.pages_count)
    TextView pagesCount;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(BaseAdapterItem baseAdapterItem) {
        final HeaderItem item = (HeaderItem) baseAdapterItem;
        pagesCount.setText(item.getPages());
    }
}
