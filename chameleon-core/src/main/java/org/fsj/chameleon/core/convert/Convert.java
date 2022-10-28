package org.fsj.chameleon.core.convert;

public interface Convert<S, T> {

    S convert(T t);
}
