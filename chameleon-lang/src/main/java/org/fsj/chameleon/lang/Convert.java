package org.fsj.chameleon.lang;

public interface Convert<T, S> {

    T convert(S var1);
}
