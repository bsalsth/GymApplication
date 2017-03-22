package com.softlica.bishal.gymapp.main;

import android.app.Application;
import android.location.Location;

import com.softlica.bishal.gymapp.di.AppModule;
import com.softlica.bishal.gymapp.di.DaggerNetComponent;
import com.softlica.bishal.gymapp.di.MainScreenComponent;
import com.softlica.bishal.gymapp.di.NetComponent;
import com.softlica.bishal.gymapp.di.NetModule;
import com.softlica.bishal.gymapp.models.GymObjectDB;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by bishal on 3/19/2017.
 */

public class App extends Application {
    private static App sInstance;
    private static NetComponent netComponent;
    private MainScreenComponent mainViewComponent;
    public static Location currentLocation;



    // Save current list // only for demo purpose
    public static List<GymObjectDB> gymObjectDBList = Collections.EMPTY_LIST;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Location dummyLoc = new Location("Dummy Location");
        dummyLoc.setLatitude(51);
        dummyLoc.setLongitude(-71);
        currentLocation = dummyLoc;
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        netComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();

    }

    public static App getInstance() {
           return sInstance;
    }

    public static NetComponent getNetComponent() {
        return netComponent;
    }
}
