package org.fsj.chameleon.lang.factory;

public interface CacheFactory<T, P> extends Factory<T, P>{

    T get(CacheFactoryParams<P> params);


    @Override
    default T get(FactoryParams<P> params) {
        return get(params);
    }
}
