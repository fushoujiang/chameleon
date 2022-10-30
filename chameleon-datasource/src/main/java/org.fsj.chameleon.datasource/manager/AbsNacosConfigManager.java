package org.fsj.chameleon.datasource.manager;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.fsj.chameleon.lang.convert.Converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public abstract class AbsNacosConfigManager<T> extends AbsConfigManager<T> implements Listener {


    private Converter<String, List<T>> changeConvert;
    private Converter<String,T> loadConvert;


    private ConfigService configService;

    private static int TIME_OUT = 3;


    public AbsNacosConfigManager(String serviceAddress) {
        init(serviceAddress);
    }

    @Override
    public T loadFromDateSource(T t) {
        try {
            final String dateId = getDateId(t);
            final String group = getGroup(t);
            String config = configService.getConfigAndSignListener(dateId, group, TIME_OUT, this);
            return loadConvert.doForward(config);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return null;
    }

    abstract String buildKey(T t);

    abstract String getDateId(T t);
    abstract String getGroup(T t);

    private void init(String serviceAddress) {
        Properties properties = new Properties();
        properties.put("serverAddr", serviceAddress);
        try {
            configService = NacosFactory.createConfigService(properties);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveConfigInfo(String configInfo) {
        final List<T> list = changeConvert.doForward(configInfo);
        Map<String ,T> map = new HashMap<>(list.size());
        list.forEach(config->{
            map.put(buildKey(config),config);
        });
        callBack(map);
    }

}
