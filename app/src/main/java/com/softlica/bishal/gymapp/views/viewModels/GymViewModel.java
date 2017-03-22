package com.softlica.bishal.gymapp.views.viewModels;

import com.softlica.bishal.gymapp.models.GymObjectDB;

import java.util.List;

/**
 * Created by bishal on 3/19/2017.
 */

public interface GymViewModel extends IViewModel {

    public void loadGymData(List<GymObjectDB> list);
}
