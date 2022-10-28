package org.fsj.chameleon.datasource.manager.cache;


public  interface CacheStore<T> {



      T get(T t);


      T put(T t);



}
