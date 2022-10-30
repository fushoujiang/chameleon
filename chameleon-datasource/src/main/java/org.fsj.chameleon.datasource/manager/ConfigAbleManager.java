package org.fsj.chameleon.datasource.manager;


import org.fsj.chameleon.lang.ConfigChangeListener;
import org.fsj.chameleon.lang.ConfigManager;
import org.fsj.chameleon.lang.Refreshable;

import java.util.List;
import java.util.Map;

public interface ConfigAbleManager<T> extends ConfigManager<T>, Refreshable<Map<String,T>>, ConfigChangeListener<Map<String,T>> {



}
