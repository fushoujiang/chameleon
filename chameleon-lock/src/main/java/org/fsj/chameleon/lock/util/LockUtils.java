package org.fsj.chameleon.lock.util;

import org.apache.commons.lang3.StringUtils;
import org.fsj.chameleon.lock.entity.LockConfig;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class LockUtils {
    /**
     * 解析加锁内容
     *
     * @param args            要加锁方法入参
     * @param lockConfigEntity 加锁配置（注解）
     * @return 要加锁的key
     */
    public static String getLockKey(Object[] args, LockConfig lockConfigEntity) {
        StringBuilder lockKey = new StringBuilder(lockConfigEntity.getLockPrefix());
        String[] keys = lockConfigEntity.getKeys();
        int index = 0;
        for (int keyIndex : lockConfigEntity.getKeyIndexes()) {
            Object arg = args[keyIndex];
            String key = keys[index++];
            if (isBasicType(key)) {
                lockKey.append("_").append(arg);
            } else {
                lockKey.append("_").append(parseKey(arg, key));
            }
        }
        return lockKey.toString();
    }
    /**
     * 判断是否基础数据类型
     *
     * @param name 要判断的占位符
     * @return true/false
     */
    private static boolean isBasicType(String name) {
        return StringUtils.equalsIgnoreCase(name, "LONG") || StringUtils.equalsIgnoreCase(name, "INT")
                || StringUtils.equalsIgnoreCase(name, "STRING");
    }

    /**
     * 解析非基础数据类型的占位符
     *
     * @param obj 占位符对应参数
     * @param key 占位符内容
     * @return 解析后的内容
     */
    private static String parseKey(Object obj, String key) {
        String[] stirs = key.substring(1).split("\\.");
        Object currObj = obj;
        for (String fieldName : stirs) {
            try {
                PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(obj.getClass(), fieldName);
                Method readMethod = propertyDescriptor.getReadMethod();
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                    readMethod.setAccessible(true);
                }
                currObj = readMethod.invoke(obj);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return String.valueOf(currObj);
    }


}
