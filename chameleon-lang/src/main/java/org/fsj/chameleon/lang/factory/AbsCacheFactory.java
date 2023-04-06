package org.fsj.chameleon.lang.factory;

import org.fsj.chameleon.lang.Refreshable;
import org.fsj.chameleon.lang.cache.CacheFreshStore;
import org.fsj.chameleon.lang.cache.CacheRefreshManager;

import java.util.Map;
import java.util.Objects;

public abstract class AbsCacheFactory <T,P> implements CacheFactory<T,P>,  Refreshable<Map<String,P>>  {

    private  CacheRefreshManager<Map<String,P>> cacheRefreshManager = CacheRefreshManager.getInstance();

    private CacheFreshStore<P> cacheFreshStore  = CacheFreshStore.getInstance();


    public AbsCacheFactory() {
        cacheRefreshManager.addRefreshListener(this);
    }


    @Override
    public void refresh(Map<String, P> map) {
        map.forEach((s, t) -> cacheFreshStore.put(s,t));
    }


    @Override
    public P get(T t) {
        final String cacheKey = getCacheKey(t);
        P p = cacheFreshStore.get(cacheKey);
        if (Objects.isNull(p)){
            p = create(t);
            cacheFreshStore.put(cacheKey,p);
        }
        return p;
    }

    public abstract P create(T t);



}
