package org.fsj.chameleon.datasource.manager;


public class LocalConfigManager<T> extends AbsConfigManager<T> {



    @Override
    public T loadFromDateSource(T t) {
        return t;
    }

    @Override
    public String buildCacheKey(T t) {
        return t.toString();
    }

}
