package aop;

import org.springframework.stereotype.Component;

@Component
class MyObservable extends Observable {
    @Override
    public void foo() {
        System.out.println("foo");
    }

    @Override
    public void bar() {
        System.out.println("bar");
    }
}
