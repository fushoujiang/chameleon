package org.fsj.chameleon.datasource.manager;


import org.fsj.chameleon.datasource.manager.cache.AbsCacheFreshStore;
import org.fsj.chameleon.datasource.manager.cache.CacheRefreshManager;

import java.util.List;
import java.util.Objects;

public abstract class AbsConfigManager<T> implements ConfigAbleManager<T> {

    private CacheRefreshManager<List<T>> cacheRefreshManager = CacheRefreshManager.getInstance();

    private AbsCacheFreshStore<T> cacheFreshStore ;
    /**
     * 从数据源获取配置
     * @param t 配置
     * @return t
     */
    public abstract T loadFromDateSource(T t);

    /**
     * 自定义自己cache
     * @param t
     * @return
     */
    public abstract String buildCacheKey(T t);



    @Override
    public T getConfig(T t) {
         T result = cacheFreshStore.get(t);
        if (Objects.isNull(result)){
            result = loadFromDateSource(t);
            cacheFreshStore.put(result);
        }
        return result;
    }

    @Override
    public void callBack(List<T> t) {
        cacheRefreshManager.fresh(t);
    }

    @Override
    public void refresh(List<T> ts) {
        cacheFreshStore.refresh(ts);
    }
}
