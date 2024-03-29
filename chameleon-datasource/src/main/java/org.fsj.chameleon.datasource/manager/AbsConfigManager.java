package org.fsj.chameleon.datasource.manager;


import org.fsj.chameleon.lang.cache.CacheFreshStore;
import org.fsj.chameleon.lang.cache.CacheRefreshManager;

import java.util.Map;
import java.util.Objects;

public abstract class AbsConfigManager<T> implements ConfigAbleManager<T> {


    public AbsConfigManager() {
        cacheRefreshManager.addRefreshListener(this);
        cacheRefreshManager.addRefreshListener(cacheFreshStore);
    }
    private CacheFreshStore<T> cacheFreshStore = CacheFreshStore.getInstance();

    private CacheRefreshManager<Map<String,T>> cacheRefreshManager = CacheRefreshManager.getInstance();
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
        final String key = buildCacheKey(t);
        T result = cacheFreshStore.get(key);
        if (Objects.isNull(result)){
            result = loadFromDateSource(t);
            cacheFreshStore.put(key,t);
        }
        return result;
    }

    @Override
    public void callBack(Map<String ,T> map) {
        cacheRefreshManager.fresh(map);
    }

    @Override
    public void refresh(Map<String,T> map) {
        cacheFreshStore.refresh(map);
    }
}
