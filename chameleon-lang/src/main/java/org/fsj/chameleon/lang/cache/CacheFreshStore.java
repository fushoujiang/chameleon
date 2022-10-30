package org.fsj.chameleon.lang.cache;

import org.fsj.chameleon.lang.Refreshable;

import java.util.Map;

public   class CacheFreshStore<T> extends DefaultCacheStore<T> implements Refreshable<Map<String,T>> {


    private CacheFreshStore() {
    }

    private static class CacheFreshStoreHolder{
        private final static CacheFreshStore instance = new CacheFreshStore();
    }

    public static  CacheFreshStore getInstance(){
        return CacheFreshStore.CacheFreshStoreHolder.instance;
    }
    @Override
    public void refresh(Map<String, T> map) {
        map.forEach((s, t) -> put(s,t));
    }
}
