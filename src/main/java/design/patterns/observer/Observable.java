package design.patterns.observer;

import java.util.ArrayList;
import java.util.List;

class Observable {
    private final List<Observation> observations = new ArrayList<>();

    private String foo;
    private String bar;

    void setFoo(String foo) {
        this.foo = foo;
        sendNotifications(foo, ChangeEventType.ONE);
    }

    void setBar(String bar) {
        this.bar = bar;
        sendNotifications(bar, ChangeEventType.TWO);
    }

    private void sendNotifications(String newValue, ChangeEventType type) {
        observations.stream()
                .filter(ob -> ob.type == type)
                .forEach(ob -> ob.o.notify(new ChangeEvent(newValue, type)));
    }

    void register(Observer o, ChangeEventType type) {
        observations.add(new Observation(o, type));
    }
}
