package com.softlica.bishal.gymapp.di;

import com.softlica.bishal.gymapp.views.ui.GymActivity;

import dagger.Component;

/**
 * Created by bishal on 3/20/2017.
 */

    @CustomScope
    @Component(dependencies = NetComponent.class, modules = MainScreenModule.class)
    public interface MainScreenComponent {
        void inject(GymActivity activity);
}