package com.michalkarmelita.marvelcomics.view.comics.adapter.model;

public class ComicAdapterItem extends BaseAdapterItem{

    private final String title;
    private final String iconUrl;
    private final Integer pageCount;
    private final Double price;
    private int id;

    public ComicAdapterItem(Integer digitalId, String title, Integer pageCount, Double price, String iconUrl) {
        this.id = digitalId;
        this.title = title;
        this.pageCount = pageCount;
        this.price = price;
        this.iconUrl = iconUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public Double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }
}
