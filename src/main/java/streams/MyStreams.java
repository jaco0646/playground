package streams;

import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableMap;

public class MyStreams {
    public static void main(String... args) {
        Map<MessageKey, MessageKeyRegistry> keyMap2 =
                Stream.of(MessageKeyRegistry.values())
                        // flatMap() requires a Stream so create one from the input value
                        // this allows mapping of the input value as well.
                        .flatMap(mkr -> mkr.getCodeToMessage().keySet().stream().map(code -> Map.entry(code, mkr)))
                        .collect(toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println(keyMap2);
    }

    static class MessageKey {
        final String key;
        MessageKey(String key) {
            this.key = key;
        }
        @Override
        public String toString() {
            return "MessageKey{" + key + '}';
        }
    }

    enum MessageKeyRegistry {
        FOO {
            private final Map<MessageKey, String> map = Map.of(
                    new MessageKey("foo1"), "foo1", new MessageKey("foo2"), "foo2");
            @Override
            Map<MessageKey, String> getCodeToMessage() {
                return map;
            }
        },
        BAR {
            private final Map<MessageKey, String> map = Map.of(new MessageKey("bar"), "bar");
            @Override
            Map<MessageKey, String> getCodeToMessage() {
                return map;
            }
        },
        BAZ {
            private final Map<MessageKey, String> map = Map.of(new MessageKey("baz"), "baz");
            @Override
            Map<MessageKey, String> getCodeToMessage() {
                return map;
            }
        };

        abstract Map<MessageKey, String> getCodeToMessage();
    }
}
