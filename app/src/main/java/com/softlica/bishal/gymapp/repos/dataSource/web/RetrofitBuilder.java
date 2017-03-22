package com.softlica.bishal.gymapp.repos.dataSource.web;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.softlica.bishal.gymapp.utilities.Constants;
import com.softlica.bishal.gymapp.main.App;
import com.softlica.bishal.gymapp.utilities.Helper;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bishal on 3/19/2017.
 */

public class RetrofitBuilder {

    OkHttpClient client = new OkHttpClient
            .Builder()
            .cache(new Cache(App.getInstance().getCacheDir(), 10 * 1024 * 1024)) // 10 MB
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (Helper.isNetworkAvailable()) {
                        request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                    } else {
                        request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                    }
                    return chain.proceed(request);
                }
            })
            .build();

    public Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.GYM_API)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return  retrofit;
    }
}
