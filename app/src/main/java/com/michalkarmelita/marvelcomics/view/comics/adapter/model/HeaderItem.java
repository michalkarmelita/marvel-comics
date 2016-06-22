package com.michalkarmelita.marvelcomics.view.comics.adapter.model;

public class HeaderItem extends BaseAdapterItem {

    private final String pages;

    public HeaderItem(String pages) {
        this.pages = pages;
    }

    public String getPages() {
        return pages;
    }
}
