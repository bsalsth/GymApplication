package com.softlica.bishal.gymapp.presenters;

import com.softlica.bishal.gymapp.main.App;
import com.softlica.bishal.gymapp.models.GymObjectDB;
import com.softlica.bishal.gymapp.repos.repoService.GymRepo;
import com.softlica.bishal.gymapp.views.MainScreenContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by bishal on 3/19/2017.
 */

public class GymActivityPresenter implements IPresenter, MainScreenContract.Presenter {
    MainScreenContract.View mView;
    CompositeDisposable compositeDisposable;
    GymRepo repo;

    @Inject
    public GymActivityPresenter(MainScreenContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void onCreate() {
        mView.showProgress();
        repo = new GymRepo();
        repo.setListener(new GymRepo.OnDownloadListener() {
            @Override
            public void onCompleted(List<GymObjectDB> list) {
            }

            @Override
            public void onSuccess(List<GymObjectDB> list) {
                mView.showPosts(list);
                App.getInstance().gymObjectDBList = list;
                mView.dismissProgress();
            }
        });
        subscribe();
    }

    public void onResume() {

    }

    public void startMapActivity(){
        if(App.getInstance().gymObjectDBList != null)
            mView.startMapActivity();
    }
    @Override
    public void loadPost() {
        mView.showProgress();
        subscribe();
    }

    @Override
    public void subscribe() {
        List<GymObjectDB> list = repo.getPost();
    }

    @Override
    public void unsubscribe() {
//
    }


    public void onPause() {
        unsubscribe();
    }
}
