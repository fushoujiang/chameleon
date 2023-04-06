package org.fsj.chameleon.datasource.manager.apollo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.google.common.base.Strings;
import org.fsj.chameleon.datasource.manager.AbsConfigManager;
import org.fsj.chameleon.lang.convert.Converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class AbsApolloConfigManager<T> extends AbsConfigManager<T> implements ConfigChangeListener{

    private  Config config;

    private Converter<ConfigChangeEvent,List<T>> apolloChangeConvert;

    private Converter<String ,T> configPropertyTConverter;

    public AbsApolloConfigManager( Config config, Converter<ConfigChangeEvent, List<T>> apolloChangeConvert, Converter<String, T> configPropertyTConverter) {
        this.config = config;
        this.apolloChangeConvert = apolloChangeConvert;
        this.configPropertyTConverter = configPropertyTConverter;
    }

    @Override
    public void onChange(ConfigChangeEvent configChangeEvent) {
        final List<T> list =  apolloChangeConvert.doForward(configChangeEvent);
        Map<String ,T> map = new HashMap<>(list.size());
        list.forEach(config->{
            map.put(buildCacheKey(config),config);
        });
        callBack(map);
    }


    @Override
    public T loadFromDateSource(T t) {
        final String key = buildCacheKey(t);
        String configProperty = config.getProperty(key, "");
        if (Strings.isNullOrEmpty(configProperty)) return t;
        return configPropertyTConverter.doForward(configProperty);
    }



}
