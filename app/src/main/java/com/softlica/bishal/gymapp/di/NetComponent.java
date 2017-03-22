package com.softlica.bishal.gymapp.di;

import com.softlica.bishal.gymapp.repos.dataSource.web.RemoteDataSource;

import javax.inject.Singleton;
import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by bishal on 3/20/2017.
 */

    @Singleton
    @Component(modules = {AppModule.class, NetModule.class})
    public interface NetComponent {
        // downstream components need these exposed with the return type
        Retrofit retrofit();
        void inject(RemoteDataSource service );
    }
