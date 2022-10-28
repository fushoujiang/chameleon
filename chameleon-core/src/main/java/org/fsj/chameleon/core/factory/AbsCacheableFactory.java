package org.fsj.chameleon.core.factory;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbsCacheableFactory<S,T> implements Factory<S,T>{

    private final ConcurrentHashMap<T,S> cache = new ConcurrentHashMap<>();

    @Override
    public S getObject(T t) {
        final S object = cache.get(t);
        if (Objects.nonNull(object)){
            return object;
        }
        return createObject(t);
    }
    abstract S createObject(T t);


}
