package org.fsj.chameleon.limit.convert;

import com.alibaba.fastjson.JSON;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import org.fsj.chameleon.lang.convert.AbstractConverter;
import org.fsj.chameleon.lang.convert.Converter;
import org.fsj.chameleon.limit.entity.RateLimiterConfig;

import java.util.ArrayList;
import java.util.List;

public class ApolloChangeConvert extends AbstractConverter<ConfigChangeEvent, List<RateLimiterConfig>> {

    public ApolloChangeConvert( ) {
        super(source->{
            List<RateLimiterConfig> limiterConfigList = new ArrayList<>(source.changedKeys().size());
            for (String key : source.changedKeys()) {
                ConfigChange change = source.getChange(key);
                RateLimiterConfig rateLimiterConfig = JSON.parseObject(change.getNewValue(), RateLimiterConfig.class);
                String[] split = key.split("_");
                rateLimiterConfig.setGroup(split[0]);
                rateLimiterConfig.setKey(split[1]);
                limiterConfigList.add(rateLimiterConfig);
            }
            return limiterConfigList;
            },null);
    }
}
