package org.fsj.chameleon.lang.factory;

public interface CacheFactory<T,P> extends Factory<T,P>{

    String getCacheKey(T t);

}
