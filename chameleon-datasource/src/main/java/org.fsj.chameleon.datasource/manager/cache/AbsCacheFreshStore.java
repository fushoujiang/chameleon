package org.fsj.chameleon.datasource.manager.cache;

import org.fsj.chameleon.lang.Refreshable;

import java.util.List;

public  abstract class AbsCacheFreshStore<T> extends AbsCacheStore<T> implements Refreshable<List<T>> {

    @Override
    public void refresh(List<T > ts) {
        ts.forEach(this::put);
    }
}
