package org.fsj.chameleon.core.key;

public interface KeyLevel {

    String getLevel(Level level);

    /**
     * 层级 I最外层，支持三层
     */
    enum Level {
        I,
        II,
        III;
    }
}
