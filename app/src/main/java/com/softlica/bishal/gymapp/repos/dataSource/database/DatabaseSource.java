package com.softlica.bishal.gymapp.repos.dataSource.database;

import android.util.Log;

import com.softlica.bishal.gymapp.main.App;
import com.softlica.bishal.gymapp.models.GymObject;
import com.softlica.bishal.gymapp.models.GymObjectDB;
import com.softlica.bishal.gymapp.repos.dataSource.IDataSource;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by bishal on 3/19/2017.
 */

public class DatabaseSource implements IDataSource {
    private static Realm myRealm;


    public DatabaseSource() {
    }


    @Override
    public Observable<List<GymObjectDB>> getAll() {
        Observable<List<GymObjectDB>> observable =
                Observable.create(new ObservableOnSubscribe<List<GymObjectDB>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<GymObjectDB>> e) throws Exception {
                        e.onNext(findAllGym());
                        e.onComplete();
                    }
                });
        return observable;
    }



    public void addGymObject(final GymObject object) {
        try {
            myRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    GymObjectDB gymObject = myRealm.createObject(GymObjectDB.class);
                    gymObject.setName(object.getName());
                    gymObject.setAddress(object.getAddress());
                    gymObject.setLatitude(object.getLocation().getLatitude());
                    gymObject.setLongitude(object.getLocation().getLongitude());
                }
            }, new Realm.Transaction.Callback() {
                @Override
                public void onSuccess() {
                    Log.d("LoCAL DATA", findAllGym().size() + "---");
                    Log.d("LoCAL DATA SIZE", "DATA SIZE CALLED");
                }

                @Override
                public void onError(Exception e) {
                    super.onError(e);
                    e.printStackTrace();
                    Log.d("From error", findAllGym().size() + "---");
                }


            });
        } finally {

        }
    }

    public List<GymObjectDB> findAllGym() {
        Realm myRealm = Realm.getInstance(App.getInstance());
        RealmResults<GymObjectDB> gymObjects =
                myRealm.where(GymObjectDB.class).findAll();
        Log.d("Saved DATA", gymObjects.size() + "");
        for (GymObjectDB g : gymObjects)
            Log.d("Saved DATA", g.getName() + " -- " + g.getAddress());

        return gymObjects;

    }

    public void clearAll() {
        myRealm.beginTransaction();
        myRealm.clear(GymObjectDB.class);
        myRealm.commitTransaction();
    }


    public Observable<List<GymObjectDB>> getAllGym() {
        Observable<List<GymObjectDB>> observable =
                Observable.create(new ObservableOnSubscribe<List<GymObjectDB>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<GymObjectDB>> e) throws Exception {
                        e.onNext(findAllGym());
                        e.onComplete();
                    }
                });
        return observable;
    }

}
