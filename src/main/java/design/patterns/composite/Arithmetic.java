package design.patterns.composite;

    public interface Arithmetic {
        double compute();
        default void appendChild(Arithmetic arithmetic) {}
        default void removeChild(Arithmetic arithmetic) {}
    }
