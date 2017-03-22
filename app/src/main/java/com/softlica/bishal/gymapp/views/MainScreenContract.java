package com.softlica.bishal.gymapp.views;

import com.softlica.bishal.gymapp.models.GymObjectDB;

import java.util.List;

/**
 * Created by bishal on 3/20/2017.
 */

public interface MainScreenContract {
    interface View {
        void showProgress();

        void dismissProgress();

        void showPosts(List<GymObjectDB> posts);

        void showError(String message);

        void showComplete();

        void startMapActivity();
    }

    interface Presenter {
        void loadPost();


    }
}