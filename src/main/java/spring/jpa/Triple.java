package spring.jpa;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import jakarta.persistence.*;

import static lombok.AccessLevel.PACKAGE;

@ImmutableEntity(tableName = "triple")
@Value
@NoArgsConstructor(access = PACKAGE, force = true)
@AllArgsConstructor(access = PACKAGE)
public class Triple {
    /**
     * In SpringBoot 3, an @Entity could be embedded in another @Entity if they share an @Id.
     * In SpringBoot 4, it seems the target must be annotated with @Embeddable instead.
     */
//    @Id
//    @Column(name = "k")
//    String key;

    @EmbeddedId
    KeyValue kv;

    @Column
    String third;

    @Embeddable
    @Value
    @NoArgsConstructor(access = PACKAGE, force = true)
    @AllArgsConstructor(access = PACKAGE)
    public static class KeyValue {
        String key;
        String value;
    }
}
