package org.fsj.chameleon.lang.factory;

import org.fsj.chameleon.lang.Refreshable;
import org.fsj.chameleon.lang.cache.AbsCacheFreshStore;
import org.fsj.chameleon.lang.cache.CacheRefreshManager;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class AbsCacheFactory <T, P > implements CacheFactory<T,  P>,  Refreshable<Map<String,T>>  {

    private CacheRefreshManager<List<FactoryParams<T>>> cacheRefreshManager = CacheRefreshManager.getInstance();

    private AbsCacheFreshStore<T> cacheFreshStore ;


    @Override
    public void refresh(Map<String, T> map) {
        map.forEach((s, t) -> cacheFreshStore.put(s,t));
    }


    @Override
    public T get(CacheFactoryParams<P> params) {
        final String cacheKey = params.getCacheKey();
        T t = cacheFreshStore.get(cacheKey);
        if (Objects.isNull(t)){
            t = create(params);
            cacheFreshStore.put(cacheKey,t);
        }
        return t;
    }


    public abstract T create(FactoryParams<P> params);



}
