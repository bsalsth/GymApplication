package com.softlica.bishal.gymapp.di;

import com.softlica.bishal.gymapp.repos.dataSource.web.RemoteDataSource;
import com.softlica.bishal.gymapp.repos.repoService.GymRepo;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by bishal on 3/20/2017.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {

    void inject(GymRepo gymRepo);

    void injectInGymRemo(RemoteDataSource remoteDataSource);
}
