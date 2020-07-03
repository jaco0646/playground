package design.patterns.chain;

import java.util.Arrays;
import java.util.List;

public class ChainOfResponsibility {
    public static void main(String... commandLineArgs) {
        List<String> args = commandLineArgs.length > 0
                ? Arrays.asList(commandLineArgs)
                : List.of("foo", "bar", "baz", "qux");

        ChainedHandler chain = new ChainedHandler("foo")
                .next(new ChainedHandler("bar")
                .next(new ChainedHandler("baz")));

        Client client = new Client(chain);
        args.forEach(client::method);
        System.out.println();

        chain.next(new ChainedHandler("qux"));
        args.forEach(client::method);
        System.out.println();

        chain.next(null);
        args.forEach(client::method);
    }

    static class Client {
        private final StringHandler argHandler;

        Client(StringHandler argHandler) {
            this.argHandler = argHandler;
        }

        void method(String arg) {
            argHandler.handle(arg);
            // more business logic...
        }
    }
}
