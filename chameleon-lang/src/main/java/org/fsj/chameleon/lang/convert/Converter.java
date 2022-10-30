package org.fsj.chameleon.lang.convert;


import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 类型转换器
 *
 * @author zhangduo -- 2017/11/17
 */
public interface Converter<A, B> {

    /**
     * 正向转换
     *
     * @param a 源对象
     * @return 转换后对象
     */
    B doForward(A a);

    /**
     * 反向转换
     *
     * @param b 源对象
     * @return 转换后对象
     */
    A doBackward(B b);

    default B doForward(A a, Function<B, Void> func) {
        final B b = doForward(a);
        func.apply(b);
        return b;
    }

    default A doBackward(B b, Function<A, Void> func) {
        final A a = doBackward(b);
        func.apply(a);
        return a;
    }

    /**
     * 正向转换集合
     *
     * @param collection 要转换的集合
     * @return 转换后集合
     */
    default List<B> listForward(Collection<A> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return Collections.EMPTY_LIST;
        }
        return collection.stream().map(this::doForward).collect(Collectors.toList());
    }

    /**
     * 反向转换集合
     *
     * @param collection 要转换的集合
     * @return 转换后的集合
     */
    default List<A> listBackward(Collection<B> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return Collections.EMPTY_LIST;
        }
        return collection.stream().map(this::doBackward).collect(Collectors.toList());
    }

}
