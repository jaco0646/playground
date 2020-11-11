package aop;

import java.util.Set;

abstract class Observable {
    private final Set<Observer> observers = Set.of(new Observer(){});

    protected void notifyObservers() {
        observers.forEach(o -> o.observeEvent(this));
    }

    public abstract void foo();
    public abstract void bar();
}
