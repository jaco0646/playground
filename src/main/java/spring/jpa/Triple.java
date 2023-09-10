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
     * An @Entity can only be embedded in another @Entity if they share an @Id.
     * Generally, the embedded class would not be another @Entity. It would be
     * annotated with @Embeddable instead.
     */
    @Id
    @Column(name = "k")
    String key;

    @Embedded
    KeyValue kv;

    @Column
    String third;
}
