package spring;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.TreeMap;

import static java.util.Collections.emptyMap;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

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
                ? new TreeMap<>(System.getProperties())
                : params.keySet().stream().collect(toMap(identity(), key -> System.getProperty(key, "")));
    }

}
