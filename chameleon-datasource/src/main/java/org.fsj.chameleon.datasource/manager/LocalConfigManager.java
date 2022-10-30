package org.fsj.chameleon.datasource.manager;


import org.fsj.chameleon.lang.factory.CacheFactoryParams;

public class LocalConfigManager<T> extends AbsConfigManager<T> {
    public LocalConfigManager(CacheFactoryParams<T> cacheFactoryParams) {
        super(cacheFactoryParams);
    }

    @Override
    public T loadFromDateSource(T t) {
        return t;
    }

    @Override
    public String buildCacheKey(T t) {
        return t.toString();
    }

}
