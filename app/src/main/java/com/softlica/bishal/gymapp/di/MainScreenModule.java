package com.softlica.bishal.gymapp.di;

/**
 * Created by bishal on 3/20/2017.
 */

import com.softlica.bishal.gymapp.views.MainScreenContract;

import dagger.Module;
import dagger.Provides;

@Module
public class MainScreenModule {
    private final MainScreenContract.View mView;


    public MainScreenModule(MainScreenContract.View mView) {
        this.mView = mView;
    }

    @Provides
    @CustomScope
    MainScreenContract.View providesMainScreenContractView() {
        return mView;
    }

}