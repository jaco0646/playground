package playground


import spock.lang.Specification

class StubSideEffect extends Specification {
    static class Event {
        final int id;
        final String data

        Event(int id, String data) {
            this.id = id
            this.data = data
        }
    }

    static class EventBus {
        void push(Event event) {}
    }

    static class Facade {
        EventBus eventBus

        Facade(EventBus eventBus) {
            this.eventBus = eventBus
        }

        int call(String data) {
            int id = new Random().nextInt()
            eventBus.push(new Event(id, data))
            return id
        }
    }

    static class Configuration {
        Facade facade(EventBus eventBus) {
            return new Facade(eventBus)
        }
    }

    def "when the method is called a proper event is emitted"() {
        given:
            EventBus eventBus = Mock()
            Facade facade = new Configuration().facade(eventBus)
            Event received
        when:
            def id = facade.call("sampleData")
        then:
            1 * eventBus.push(_ as Event) >> { Event event -> received = event }
            received.id == id
    }

}
