package com.michalkarmelita.marvelcomics.api;

import com.michalkarmelita.marvelcomics.api.model.ComicsResponse;

import retrofit2.http.GET;
import rx.Observable;

public interface MarvelApiService {

    @GET("/v1/public/comics")
    Observable<ComicsResponse> getComics();

}
