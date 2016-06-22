package com.michalkarmelita.marvelcomics.utils;

import com.michalkarmelita.marvelcomics.view.comics.adapter.model.BaseAdapterItem;
import com.michalkarmelita.marvelcomics.view.comics.adapter.model.ComicAdapterItem;

import java.util.Comparator;

public class ComicPriceComparator implements Comparator<BaseAdapterItem> {
    @Override
    public int compare(BaseAdapterItem first, BaseAdapterItem second) {
        return ((ComicAdapterItem) first).getPrice().compareTo(((ComicAdapterItem) second).getPrice());
    }
}
