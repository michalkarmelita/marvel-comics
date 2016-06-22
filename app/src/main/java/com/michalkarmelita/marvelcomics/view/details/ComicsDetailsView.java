package com.michalkarmelita.marvelcomics.view.details;

public interface ComicsDetailsView {

    void setImage(String imageUrl);

    void setTitle(String title);

    void setCreators(String creators);

    void setDescription(String description);

    void setPages(String pages);

    void setPrice(String price);
}
