package spring.jpa

import org.hibernate.annotations.Immutable
import spock.lang.Specification

import jakarta.persistence.Entity
import jakarta.persistence.Table

class ByteBuddySpec extends Specification {

    def 'Assert ByteBuddy added annotations'() {
        expect:
            KeyValue.class.declaredAnnotations.size() == 3
            KeyValue.class.getAnnotationsByType(Immutable)
            KeyValue.class.getAnnotationsByType(Entity)
            KeyValue.class.getAnnotationsByType(Table).first().name() == 'playground'
    }

}
