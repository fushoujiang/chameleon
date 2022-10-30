package org.fsj.chameleon.datasource.manager;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.fsj.chameleon.lang.convert.Converter;
import org.fsj.chameleon.lang.factory.CacheFactoryParams;

import java.util.HashMap;
import java.util.Map;

public abstract class AbsNacosConfigManager<T> extends AbsConfigManager<T> implements Listener {


    private Converter<String, T> converter;

    private ConfigService configService;


    public AbsNacosConfigManager(CacheFactoryParams<T> cacheFactoryParams, Converter<String, T> converter, ConfigService configService) {
        super(cacheFactoryParams);
        this.converter = converter;
        this.configService = configService;
    }

    private static int TIME_OUT = 3;


    @Override
    public T loadFromDateSource(T t) {
        try {
            final String dateId = getDateId(t);
            final String group = getGroup(t);
            String config = configService.getConfigAndSignListener(dateId, group, TIME_OUT, this);
            return converter.doForward(config);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return null;
    }


    public abstract String getDateId(T t);

    public abstract String getGroup(T t);


    @Override
    public void receiveConfigInfo(String configInfo) {
        final T t = converter.doForward(configInfo);
        Map<String, T> map = new HashMap<>(1);
        map.put(buildCacheKey(t), t);
        callBack(map);
    }

}
