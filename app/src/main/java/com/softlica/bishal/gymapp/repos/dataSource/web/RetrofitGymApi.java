package com.softlica.bishal.gymapp.repos.dataSource.web;

import com.softlica.bishal.gymapp.models.GymObject;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by bishal on 3/18/2017.
 */

public interface RetrofitGymApi {

     // retrofit annotation to get data from API
    @GET("vitalize-mobile/gyms.json")
    Observable<List<GymObject>> getGymData();
}
