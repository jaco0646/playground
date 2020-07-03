package design.patterns.chain;

import java.util.Optional;

class ChainedHandler implements StringHandler {
    private final String handledString;
    private ChainedHandler next;

    ChainedHandler(String handledString) {
        this.handledString = handledString;
    }

    Optional<ChainedHandler> next() {
        return Optional.ofNullable(next);
    }

    ChainedHandler next(ChainedHandler handler) {
        ChainedHandler subsequent = next;
        next = handler;
        if (handler != null && subsequent != null)
            handler.next(subsequent);
        return this;
    }

    @Override
    public void handle(String arg) {
        if (arg.equalsIgnoreCase(handledString)) {
            System.out.println("Handled: " + arg);
        } else {
            next().ifPresentOrElse(
                    handler -> handler.handle(arg),
                    () -> System.out.println("No handler for: " + arg));
        }
    }
}
