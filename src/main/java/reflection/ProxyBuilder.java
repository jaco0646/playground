package reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.function.Function;

import static reflection.ReflectUtil.getParents;

public class ProxyBuilder<T> {
    private final T delegate;
    private final Class<?>[] interfaces;

    public ProxyBuilder(T delegate) {
        this.delegate = delegate;
        this.interfaces = getParents(delegate.getClass()).filter(Class::isInterface).toArray(Class[]::new);
        if (interfaces.length == 0) {
            throw new IllegalArgumentException("Delegate object has no interface: " + delegate);
        }
    }

    @SuppressWarnings("unchecked")
    public T newProxy(InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(delegate.getClass().getClassLoader(), interfaces, handler);
    }

    public InvocationHandler newHandler(Map<Method, Function<Object[], Object>> overrides) {
        return (proxy, method, args) -> {
            if (overrides.containsKey(method)) {
                return overrides.get(method).apply(args);
            } else {
                return method.invoke(delegate, args);
            }
        };
    }
}
