package com.softlica.bishal.gymapp.utilities;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.softlica.bishal.gymapp.main.App;
import com.softlica.bishal.gymapp.models.GymObject;
import com.softlica.bishal.gymapp.models.GymObjectDB;

/**
 * Created by bishal on 3/19/2017.
 */

public class Helper {

    public static GymObjectDB JsonToDB(GymObject g) {
        GymObjectDB gymObjectDB = new GymObjectDB();
        gymObjectDB.setName(g.getName());
        gymObjectDB.setAddress(g.getAddress());
        gymObjectDB.setDistance(1.0);
        gymObjectDB.setWebsite(g.getWebsite());
        gymObjectDB.setLatitude(g.getLocation().getLatitude());
        gymObjectDB.setLongitude(g.getLocation().getLongitude());
        return gymObjectDB;
    }

    public static  boolean isNetworkAvailable() {
        ConnectivityManager connectivityMgr = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        /// if no network is available networkInfo will be null
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public static  double getDistance(Location a, Location b){
//        b = new Location("");
//        b.setLatitude(42.361219);
//        b.setLongitude(-71.069623);
//        a = new Location("A");
//        a.setLatitude(42.361219);
//        a.setLongitude(-71.06962);
        return a.distanceTo(b);
    }
}
