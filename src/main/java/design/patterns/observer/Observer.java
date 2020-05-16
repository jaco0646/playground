package design.patterns.observer;

class Observer {
    void notify(ChangeEvent ce) {
        System.out.println("Received change event of type: "+ ce.type +", with new value: "+ ce.newValue);
    }
}
