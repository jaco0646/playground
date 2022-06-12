package spring.async;

import org.springframework.core.NamedThreadLocal;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Optional;

/**
 * Spring may store the Authorization token in other ThreadLocal contexts,
 * such as {@link RequestContextHolder} or {@link SecurityContextHolder};
 * however, the lifecycles of these contexts are controlled by Spring,
 * and may not align with the lifespan of a multi-threaded operation.
 *
 * Spring contexts may be cleared before background threads complete,
 * so this class stores the authorization token in a context
 * whose lifecycle is controlled by the application.
 */
public class AuthTokenContextHolder {
    private static final ThreadLocal<String> AUTH_TOKEN = new NamedThreadLocal<>("Authorization Token");

    private AuthTokenContextHolder(){}

    public static void setAuthToken(String authToken) {
        AUTH_TOKEN.set(authToken);
    }

    public static Optional<String> getAuthToken() {
        return Optional.ofNullable(AUTH_TOKEN.get());
    }

    public static void clearAuthToken() {
        AUTH_TOKEN.remove();
    }
}
