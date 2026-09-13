package streams;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static streams.Utils.entriesToMap;

public class MapMulti {
    static final Map<String, Optional<Object>> OPTIONALS =
            Map.of(
                    "one", Optional.of("foo"),
                    "two", Optional.empty(),
                    "three", Optional.of("bar"),
                    "four", Optional.empty(),
                    "five", Optional.of("baz")
            );

    static void main() {
        Map<String, String> map = OPTIONALS.keySet().stream()
                .mapMulti(MapMulti::mapValueIfPresent)
                .collect(entriesToMap());
    }

    static void mapValueIfPresent(String key, Consumer<Map.Entry<String, String>> addToStream) {
        OPTIONALS.get(key)
                .map(val -> Map.entry(key, val.toString()))
                .ifPresent(addToStream);
    }

}
