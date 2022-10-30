package org.fsj.chameleon.lang.factory;

public interface Factory<T,P> {

    T get(FactoryParams<P> params);
}
