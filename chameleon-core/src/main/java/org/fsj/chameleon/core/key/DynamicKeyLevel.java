package org.fsj.chameleon.core.key;

public class DynamicKeyLevel implements KeyLevel {

    private String I;
    private String II;
    private String III;

    public DynamicKeyLevel(String I, String II, String III) {
        this.I = I;
        this.II = II;
        this.III = III;
    }

    @Override
    public String getLevel(Level level) {
        if (level == Level.I) {
            return I;
        }
        if (level == Level.II) {
            return I;
        }
        return III;
    }
}
