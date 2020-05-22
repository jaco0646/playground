package reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static reflection.ReflectUtil.getParents;

/** Helper methods for {@link java.lang.reflect.Proxy#newProxyInstance(ClassLoader, Class[], InvocationHandler)}
 *  Only interface methods may be overridden. Methods from abstract or concrete classes are not supported. */
public class ProxyBuilder<T> {
    private final ClassLoader classLoader;
    private final Class<?>[] interfaces;
    private final InvocationHandler handler;
    private final Map<Method, Function<Object[], Object>> overrides = new HashMap<>();

    public ProxyBuilder(T delegate) {
        this.classLoader = delegate.getClass().getClassLoader();
        this.interfaces = getParents(delegate.getClass()).filter(Class::isInterface).toArray(Class[]::new);
        if (interfaces.length == 0) {
            throw new IllegalArgumentException("Delegate class has no interface: " + delegate.getClass().getSimpleName());
        }
        this.handler = (proxy, method, args) ->
                overrides.containsKey(method) ? overrides.get(method).apply(args) : method.invoke(delegate, args);
    }

    public ProxyBuilder<T> withOverride(String methodName, Function<Object[], Object> override) {
        return Stream.of(interfaces)
                .flatMap(intrface -> Stream.of(intrface.getMethods()))
                .filter(method -> method.getName().equals(methodName))
                .reduce((x,y) -> { throw new IllegalArgumentException("Method is overloaded: " + methodName); })
                .map(method -> withOverride(method, override))
                .orElseThrow(() -> new IllegalArgumentException("Method not found: " + methodName));
    }

    public ProxyBuilder<T> withOverride(Method method, Function<Object[], Object> override) {
        overrides.put(method, override);
        return this;
    }

    public ProxyBuilder<T> withOverrides(Map<Method, Function<Object[], Object>> overrides) {
        this.overrides.putAll(overrides);
        return this;
    }

    @SuppressWarnings("unchecked")
    public T build() {
        return (T) Proxy.newProxyInstance(classLoader, interfaces, handler);
    }
}
