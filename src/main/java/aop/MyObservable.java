package aop;

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
