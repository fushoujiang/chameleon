package org.fsj.chameleon.datasource.manager;


import org.fsj.chameleon.datasource.manager.cache.CacheRefreshManager;
import org.fsj.chameleon.datasource.manager.cache.CacheStore;
import org.fsj.chameleon.lang.Refreshable;

import java.util.List;
import java.util.Objects;

public abstract class AbsConfigManager<T> implements ConfigAbleManager<T> {

    private CacheRefreshManager<List<T>> cacheRefreshManager = CacheRefreshManager.getInstance();

    private CacheStore<T> cacheStore ;
    /**
     * 从数据源获取配置
     * @param t 配置
     * @return t
     */
    public abstract T loadFromDateSource(T t);


    @Override
    public T getConfig(T t) {
         T result = cacheStore.get(t);
        if (Objects.isNull(result)){
            result = loadFromDateSource(t);
            cacheStore.put(result);
        }
        return result;
    }

    @Override
    public void callBack(List<T> t) {
        cacheRefreshManager.fresh(t);
    }

    @Override
    public void refresh(List<T> ts) {
        if (cacheStore instanceof Refreshable<List<T>>){
            ((Refreshable<?>) cacheStore).refresh(ts);
        }
    }
}
