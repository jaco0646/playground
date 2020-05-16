package design.patterns.observer;

class ChangeEvent {
    final String newValue;
    final ChangeEventType type;

    ChangeEvent(String newValue, ChangeEventType type) {
        this.newValue = newValue;
        this.type = type;
    }
}
