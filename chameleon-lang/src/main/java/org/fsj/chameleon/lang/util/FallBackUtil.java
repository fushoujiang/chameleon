package org.fsj.chameleon.lang.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;

public class FallBackUtil {

    protected static Method findFallbackMethod(ProceedingJoinPoint joinPoint, String fallbackMethodName) throws NoSuchMethodException{
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();
        //这里通过判断必须取和原方法一样参数的fallback方法
        return joinPoint.getTarget().getClass().getMethod(fallbackMethodName, parameterTypes);
    }

    private static final String NULL_FALLBACK_METHOD = "nullFallbackMethod";
    private static final String EMPTY_FALLBACK_METHOD = "emptyFallbackMethod";

    public static Object invokeFallbackMethod(ProceedingJoinPoint joinPoint, String fallback) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (NULL_FALLBACK_METHOD.equals(fallback)) {
            return null;
        }
        if (EMPTY_FALLBACK_METHOD.equals(fallback)) {
            return Collections.EMPTY_LIST;
        }
        Method method = findFallbackMethod(joinPoint, fallback);
        method.setAccessible(true);
        return method.invoke(joinPoint.getTarget(), joinPoint.getArgs());
    }
}
