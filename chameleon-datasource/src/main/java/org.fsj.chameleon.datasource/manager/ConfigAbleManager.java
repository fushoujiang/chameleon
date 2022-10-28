package org.fsj.chameleon.datasource.manager;


import org.fsj.chameleon.lang.ConfigChangeListener;
import org.fsj.chameleon.lang.ConfigManager;
import org.fsj.chameleon.lang.Refreshable;

import java.util.List;

public interface ConfigAbleManager<T> extends ConfigManager<T>, Refreshable<List<T>>, ConfigChangeListener<List<T>> {



}
