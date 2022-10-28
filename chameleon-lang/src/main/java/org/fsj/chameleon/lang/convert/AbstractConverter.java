package org.fsj.chameleon.lang.convert;

import java.util.function.Function;

/**
 * 类型转换器
 *
 * @author fsj
 */
public abstract class AbstractConverter<A, B> implements Converter<A, B> {

    private final Function<A, B> forwardFunction;
    private final Function<B, A> backwardFunction;

    protected AbstractConverter(Function<A, B> forwardFunction, Function<B, A> backwardFunction) {
        this.forwardFunction = forwardFunction;
        this.backwardFunction = backwardFunction;
    }

    @Override
    public B doForward(A a) {
        if (forwardFunction == null) {
            throw new UnsupportedOperationException();
        }
        if (a == null) {
            return null;
        }
        return forwardFunction.apply(a);
    }

    @Override
    public A doBackward(B b) {
        if (backwardFunction == null) {
            throw new UnsupportedOperationException();
        }
        if (b == null) {
            return null;
        }
        return backwardFunction.apply(b);
    }

}
