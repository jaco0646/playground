package spring.jackson;

public interface MrBeanProjection2 extends MrBeanProjection {
    /** Re-abstracting this method causes MrBean to implement an override.
     * Without this, MrBean will not expose any overriding json value (in favor of the default method). */
    @Override
    String getQux();
}
