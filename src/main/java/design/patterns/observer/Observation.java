package design.patterns.observer;

class Observation {
    final Observer o;
    final ChangeEventType type;

    Observation(Observer o, ChangeEventType type) {
        this.o = o;
        this.type = type;
    }
}
