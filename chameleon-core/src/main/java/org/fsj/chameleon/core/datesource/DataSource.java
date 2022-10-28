package org.fsj.chameleon.core.datesource;

public interface DataSource<S,T> {

    S loadSource(T t);

}
