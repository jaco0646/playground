package spring;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.TreeMap;

import static java.util.Collections.emptyMap;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

/**
 * This controller in combination with a {@code /restart} endpoint can be used to update Spring application properties,
 * without redeploying the application, by setting a json value for the {@code SPRING_APPLICATION_JSON} property.
 * <p>
 * For example, an HTTP encoded value such as {@code %7B%22message%22%3A%22runtime-json-message%22%7D} sets the Spring
 * {@code message} property to a value of {@code runtime-json-message}.
 *
 * @see <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config"/>
 *               https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config</a>
 */
@RestController
public class SystemPropertiesController {

    @PostMapping("/env")
    public Map<?,?> setParams(@RequestParam Map<String, String> params) {
        params.forEach(System::setProperty);
        return getParams(emptyMap());
    }

    @DeleteMapping("/env")
    public Map<?,?> deleteParams(@RequestParam Map<String, String> params) {
        params.keySet().forEach(System::clearProperty);
        return getParams(emptyMap());
    }

    @GetMapping("/env")
    public Map<?,?> getParams(@RequestParam Map<String, String> params) {
        return params.isEmpty()
                ? getSystemProperties()
                : params.keySet().stream().collect(toMap(identity(), key -> System.getProperty(key, "")));
    }

    private Map<?,?> getSystemProperties() {
        Map<?, ?> sysProps = new TreeMap<>(System.getProperties());
        // Exclude keys with very long values to improve readability.
        // System properties are available in read-only json format via Spring Boot Actuator anyway.
        sysProps.remove("java.class.path");
        sysProps.remove("java.library.path");
        return sysProps;
    }

}
