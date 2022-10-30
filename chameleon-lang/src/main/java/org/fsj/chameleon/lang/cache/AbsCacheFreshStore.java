package org.fsj.chameleon.lang.cache;

import org.fsj.chameleon.lang.Refreshable;

import java.util.List;
import java.util.Map;

public  abstract class AbsCacheFreshStore<T> extends AbsCacheStore<T> implements Refreshable<Map<String,T>> {

    @Override
    public void refresh(Map<String, T> map) {
        map.forEach((s, t) -> put(s,t));
    }
}
