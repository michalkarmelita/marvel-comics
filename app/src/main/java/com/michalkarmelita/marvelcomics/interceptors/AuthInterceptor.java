package com.michalkarmelita.marvelcomics.interceptors;

import com.michalkarmelita.marvelcomics.api.ApiConsts;
import com.michalkarmelita.marvelcomics.utils.Md5Util;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    @Inject
    public AuthInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final String timestamp = String.valueOf(System.currentTimeMillis());
        final Request request = chain.request();
        final HttpUrl url = request.url().newBuilder()
                .addQueryParameter("ts", timestamp)
                .addQueryParameter("apikey", ApiConsts.PUBLIC_KEY)
                .addQueryParameter("hash", Md5Util.generateMd5Hash(timestamp + ApiConsts.PRIVATE_KEY + ApiConsts.PUBLIC_KEY))
                .build();
        return chain.proceed(request.newBuilder().url(url).build());
    }
}
