package org.fsj.chameleon.core.factory;

public interface Factory<S,T> {

    S getObject(T t);
}
