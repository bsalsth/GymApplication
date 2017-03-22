package com.softlica.bishal.gymapp.repos.repoService;


import java.util.List;

/**
 * Created by bishal on 3/18/2017.
 */

public interface IRepository {
   public <T> List<T> getPost();

      public   <T> void  add(T t);
}
