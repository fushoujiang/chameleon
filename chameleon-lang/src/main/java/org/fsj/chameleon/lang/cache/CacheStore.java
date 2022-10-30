package org.fsj.chameleon.lang.cache;

public interface CacheStore<T> {

    T get(String key);

    T put(String key, T t);
}
