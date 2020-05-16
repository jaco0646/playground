package design.patterns.observer;

public class MainObserver {
    public static void main(String... args) {
        Observer observer = new Observer();
        Observable subject = new Observable();
        subject.register(observer, ChangeEventType.ONE);
        subject.register(observer, ChangeEventType.TWO);
        subject.setFoo("Hello ");
        subject.setBar("World.");
    }
}
