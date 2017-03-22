package com.softlica.bishal.gymapp.repos.repoService;

import android.util.Log;

import com.softlica.bishal.gymapp.main.App;
import com.softlica.bishal.gymapp.models.GymObjectDB;
import com.softlica.bishal.gymapp.repos.dataSource.database.RealmSource;
import com.softlica.bishal.gymapp.repos.dataSource.web.RemoteDataSource;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

/**
 * Created by bishal on 3/20/2017.
 */

public class GymRepo implements IRepository {
    List<GymObjectDB> list;
    private OnDownloadListener listener;
    RemoteDataSource remote;
    RealmSource databaseSource;
    List<GymObjectDB> gymObjectDBs;
    CompositeDisposable compositeDisposable;


    public GymRepo() {
        remote = new RemoteDataSource();
        databaseSource = RealmSource.with(App.getInstance());
        compositeDisposable = new CompositeDisposable();
    }


    //    setting download listener from presenter
    public void setListener(OnDownloadListener listener) {
        this.listener = listener;
    }


    @Override
    public <T> List<T> getPost() {
        Observable<List<GymObjectDB>> remoteObservable = remote.getAll();
//
        compositeDisposable.add(remoteObservable.
                subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<GymObjectDB>>() {
                    @Override
                    public void onNext(List<GymObjectDB> value) {

                        if (value == null || value.size() < 1) {
                            list = Realm.getDefaultInstance().where(GymObjectDB.class).findAll();
                            listener.onSuccess(list);
                            Log.d("Realm", " --- size - " + list.size() + " --- ");
                        }
                        listener.onSuccess(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                })
        );

//        }

//        Observable<List<GymObjectDB>> databaseObservable = databaseSource.getAll();
//        compositeDisposable.add(databaseObservable.
//                subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableObserver<List<GymObjectDB>>() {
//                    @Override
//                    public void onNext(List<GymObjectDB> value) {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                })
//        );
        return null;
    }

    @Override
    public <T> void add(T t) {

    }


    public interface OnDownloadListener {

        public void onCompleted(List<GymObjectDB> list);

        public void onSuccess(List<GymObjectDB> list);
    }
}
