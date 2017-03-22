package com.softlica.bishal.gymapp.repos.dataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by bishal on 3/20/2017.
 */

public interface IDataSource<T> {
    public  <T>  Observable<List<T>> getAll();

}
