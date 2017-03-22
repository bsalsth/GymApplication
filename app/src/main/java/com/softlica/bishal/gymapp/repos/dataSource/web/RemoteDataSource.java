package com.softlica.bishal.gymapp.repos.dataSource.web;

import android.location.Location;
import android.util.Log;

import com.softlica.bishal.gymapp.main.App;
import com.softlica.bishal.gymapp.models.GymObject;
import com.softlica.bishal.gymapp.models.GymObjectDB;
import com.softlica.bishal.gymapp.repos.dataSource.IDataSource;
import com.softlica.bishal.gymapp.repos.dataSource.database.DatabaseSource;
import com.softlica.bishal.gymapp.utilities.Helper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Retrofit;


/**
 * Created by bishal on 3/18/2017.
 */

public class RemoteDataSource implements IDataSource {


    // Call back for data loading
    CompositeDisposable compositeDisposable;

    Retrofit retrofit = new RetrofitBuilder().getRetrofit();
    final RetrofitGymApi service;

    // for datastorage
    DatabaseSource source = new DatabaseSource();

    public RemoteDataSource() {
        compositeDisposable = new CompositeDisposable();
        service = retrofit.create(RetrofitGymApi.class);


    }

    @Override
    public Observable<List<GymObjectDB>> getAll() {
        Observable<List<GymObjectDB>> observable = service.getGymData()
                .map(new Function<List<GymObject>, List<GymObjectDB>>() {
                    @Override
                    public List<GymObjectDB> apply(List<GymObject> gymObjects) throws Exception {
                        List<GymObjectDB> list = new ArrayList<GymObjectDB>();
                        Realm realm = Realm.getInstance(App.getInstance());
                        for (GymObject g : gymObjects) {
//                            For demo purpose only
                            int randomNum = 0 + (int) (Math.random() * 5000);
                            g.setId(randomNum);
                            Location dest = new Location("Gym Location");
                            dest.setLongitude(g.getLocation().getLongitude());
                            dest.setLatitude(g.getLocation().getLatitude());
                            GymObjectDB gym = Helper.JsonToDB(g);
                            gym.setDistance(Helper.getDistance(App.currentLocation, dest));
                            list.add(gym);
                            realm.beginTransaction();
                            GymObjectDB i = realm.copyToRealmOrUpdate(Helper.JsonToDB(g));
                            realm.commitTransaction();
                            Log.d("DATABASE_LOG", " --- ADDDED TO DATABASE - " + i.getId());
                        }

                        return list;
                    }
                });
        return observable;
    }

    public CompositeDisposable getGymFromWeb() {
        final RetrofitGymApi service = retrofit.create(RetrofitGymApi.class);
        Observable<List<GymObject>> observable = service.getGymData()
                .map(new Function<List<GymObject>, List<GymObject>>() {
                    @Override
                    public List<GymObject> apply(final List<GymObject> gymObjects) throws Exception {
                        List<GymObject> list = null;
                        //get realm instance
                        list = gymObjects;
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    int id = 1;
                                    Realm realm = Realm.getInstance(App.getInstance());
                                    for (GymObject g : gymObjects) {
                                        RealmResults<GymObjectDB> allTransactions =
                                                realm.where(GymObjectDB.class).findAllSorted("createdAt");
                                        long lastInsertedId = allTransactions.last().getId();
                                        long primaryKey = (lastInsertedId == 0) ? 1 : lastInsertedId + 1;
                                        g.setId(primaryKey);
                                        realm.beginTransaction();
                                        GymObjectDB i = realm.copyToRealmOrUpdate(Helper.JsonToDB(g));
                                        realm.commitTransaction();
                                        Log.d("DATABASE_ADD", " --- ADDDED - " + i.getId());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread.start();


                        return list;
                    }
                });

        compositeDisposable.add(observable.
                subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<GymObject>>() {
                    @Override
                    public void onNext(List<GymObject> value) {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                })
        );
        return compositeDisposable;
    }


}
