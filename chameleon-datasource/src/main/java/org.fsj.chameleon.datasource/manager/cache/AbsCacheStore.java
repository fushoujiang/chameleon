package org.fsj.chameleon.datasource.manager.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbsCacheStore<T> implements CacheStore<T> {

    private  final Map<String,T> cache = new ConcurrentHashMap();


    abstract String buildCacheKey(T t);

    @Override
    public T get(T t) {
        return cache.get(buildCacheKey(t));
    }

    @Override
    public T put(T t) {
        return cache.put(buildCacheKey(t),t);
    }
}
