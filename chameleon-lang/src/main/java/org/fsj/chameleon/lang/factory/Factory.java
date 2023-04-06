package org.fsj.chameleon.lang.factory;

public interface Factory<T,P>{

    P get(T t);
}
