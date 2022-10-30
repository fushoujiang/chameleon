package org.fsj.chameleon.lang.factory;

public interface CacheFactoryParams<T> extends FactoryParams<T>{

    String getCacheKey();

}
