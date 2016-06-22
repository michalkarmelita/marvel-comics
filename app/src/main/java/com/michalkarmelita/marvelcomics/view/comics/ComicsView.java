package com.michalkarmelita.marvelcomics.view.comics;

import com.michalkarmelita.marvelcomics.view.comics.adapter.model.BaseAdapterItem;
import com.michalkarmelita.marvelcomics.view.comics.adapter.model.HeaderItem;

import java.util.List;

public interface ComicsView {

    void setfilteredComicsList(List<BaseAdapterItem> adapterItems);

    void updateComicsList(List<BaseAdapterItem> adapterItems);

    void setPages(HeaderItem header);
}
