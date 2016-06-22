package com.michalkarmelita.marvelcomics.view.comics.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.michalkarmelita.marvelcomics.R;
import com.michalkarmelita.marvelcomics.dagger.daggerqualifiers.ForActivity;
import com.michalkarmelita.marvelcomics.view.comics.adapter.model.BaseAdapterItem;
import com.michalkarmelita.marvelcomics.view.comics.adapter.model.ComicAdapterItem;
import com.michalkarmelita.marvelcomics.view.comics.adapter.model.HeaderItem;
import com.michalkarmelita.marvelcomics.view.comics.adapter.viewholders.ComicsAdapterBaseViewHolder;
import com.michalkarmelita.marvelcomics.view.comics.adapter.viewholders.ComicsViewHolder;
import com.michalkarmelita.marvelcomics.view.comics.adapter.viewholders.HeaderViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapterBaseViewHolder> {

    public interface ComicClickListener {

        void onComicClick(int itemId);

    }

    private static final int HEADER_ITEM = 0;
    private static final int COMICS_ITEM = 1;

    private List<BaseAdapterItem> items;

    private final Context context;
    private final LayoutInflater inflater;
    private final ComicClickListener clickListener;

    public ComicsAdapter(@ForActivity Context context, LayoutInflater inflater, ComicClickListener clickListener) {
        this.items = new ArrayList<>();
        this.context = context;
        this.inflater = inflater;
        this.clickListener = clickListener;
    }

    @Override
    public ComicsAdapterBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER_ITEM:
                return new HeaderViewHolder(inflater.inflate(R.layout.comics_lisr_header, parent, false));
            case COMICS_ITEM:
                return new ComicsViewHolder(inflater.inflate(R.layout.comics_list_item, parent, false), context, clickListener);
            default:
                throw new RuntimeException("Unsupported view type");
        }
    }

    @Override
    public void onBindViewHolder(ComicsAdapterBaseViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        final BaseAdapterItem item = items.get(position);
        if (item instanceof ComicAdapterItem) {
            return COMICS_ITEM;
        } else {
            return HEADER_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<BaseAdapterItem> adapterItems) {
        items.clear();
        items.addAll(adapterItems);
        notifyDataSetChanged();
    }

    public void addItems(List<BaseAdapterItem> adapterItems) {
        items.addAll(adapterItems);
        notifyItemRangeChanged(items.size(), adapterItems.size());
    }

    public void addHeader(HeaderItem header) {
        if (items.size() > 0 && items.get(0) instanceof HeaderItem) {
            items.remove(0);
            items.add(0, header);
        } else {
            items.add(0, header);
        }
        notifyDataSetChanged();
    }
}
