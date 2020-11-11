package aop;

interface Observer {
    default void observeEvent(Observable event) {
        System.out.println("Event observed: " + event);
    }
}
