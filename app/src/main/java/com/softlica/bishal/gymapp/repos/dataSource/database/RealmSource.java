package com.softlica.bishal.gymapp.repos.dataSource.database;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.softlica.bishal.gymapp.models.GymObject;
import com.softlica.bishal.gymapp.models.GymObjectDB;
import com.softlica.bishal.gymapp.repos.dataSource.IDataSource;
import com.softlica.bishal.gymapp.utilities.Helper;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by bishal on 3/19/2017.
 */

public class RealmSource implements IDataSource {
    private static RealmSource instance;
    private  Realm realm;

    public RealmSource(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmSource with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmSource(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmSource with(Activity activity) {

        if (instance == null) {
            instance = new RealmSource(activity.getApplication());
        }
        return instance;
    }

    public static RealmSource with(Application application) {

        if (instance == null) {
            instance = new RealmSource(application);
        }
        return instance;
    }

    public static RealmSource getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from GymObjectDB.class
    public void clearAll() {

        realm.beginTransaction();
        realm.clear(GymObjectDB.class);
        realm.commitTransaction();
    }

    //find all objects in the GymObjectDB.class
    public RealmResults<GymObjectDB> getAllGyms() {
//        realm = Realm.getDefaultInstance();
//        return realm.where(GymObjectDB.class).findAll();
        return null;
    }

    public GymObjectDB add(GymObject g) {
        RealmResults<GymObjectDB> allTransactions =
                realm.where(GymObjectDB.class).findAllSorted("createdAt");
        //If you have an incrementing id column, do this
        long lastInsertedId = allTransactions.last().getId();
        long primaryKey = (lastInsertedId == 0) ? 1 : lastInsertedId + 1;
        g.setId(primaryKey);
        realm.beginTransaction();
        GymObjectDB gymObjectDB = realm.copyToRealmOrUpdate(Helper.JsonToDB(g));
        realm.commitTransaction();
        return gymObjectDB;
    }

    //query a single item with the given id
    public GymObjectDB getGymObjectDB(String id) {

        return realm.where(GymObjectDB.class).equalTo("id", id).findFirst();
    }

    //check if GymObjectDB.class is empty
    public boolean hasGymObjectDBs() {

        return !realm.allObjects(GymObjectDB.class).isEmpty();
    }

    //query example
    public RealmResults<GymObjectDB> queryedGymObjectDBs() {

        return realm.where(GymObjectDB.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }

    public void closeAll() {
        realm.close();
        instance = null;

    }


    @Override
    public Observable<List<GymObjectDB>> getAll() {
        Observable<List<GymObjectDB>> observable =
                Observable.create(new ObservableOnSubscribe<List<GymObjectDB>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<GymObjectDB>> e) throws Exception {
                        e.onNext(getAllGyms());
                        e.onComplete();
                    }
                });
        return observable;
    }
}
