package org.fsj.chameleon.core;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DataSourceRepository<T> {
    public static DataSourceRepository getInstance() {
        return DataSourceRepositoryHandler.INSTANCE;
    }

    private static class DataSourceRepositoryHandler {
        private static final DataSourceRepository INSTANCE = new DataSourceRepository();
    }
    private final ConcurrentMap<String, T> REMOTE_CONFIG_CACHE = new ConcurrentHashMap<>();


}
