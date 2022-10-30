package org.fsj.chameleon.lang.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbsCacheStore<T> implements CacheStore<T> {

    private  final Map<String,T> cache = new ConcurrentHashMap();



    @Override
    public T get(String key) {
        return cache.get(key);
    }

    @Override
    public T put(String  key,T t) {
        return cache.put(key,t);
    }
}
