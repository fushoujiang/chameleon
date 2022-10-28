package org.fsj.chameleon.datasource.manager;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.google.common.base.Strings;
import org.fsj.chameleon.lang.convert.Converter;

import java.util.List;


public abstract class AbsApolloConfigManager<T> extends AbsConfigManager<T> implements ConfigChangeListener{

    private  Config config;

    private Converter<List<T>,ConfigChangeEvent> apolloChangeConvert;

    private Converter<String ,T> configPropertyTConverter;





    @Override
    public void onChange(ConfigChangeEvent configChangeEvent) {
        callBack(apolloChangeConvert.doBackward(configChangeEvent));
    }


    @Override
    public T loadFromDateSource(T t) {
        final String key = buildCacheKey(t);
        String configProperty = config.getProperty(key, "");
        if (Strings.isNullOrEmpty(configProperty)) return t;
        return configPropertyTConverter.doForward(configProperty);
    }

}
