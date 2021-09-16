package spring.jpa;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Id;

import static lombok.AccessLevel.PACKAGE;

/*
    Three annotations will be added by ByteBuddy,
        when it encounters the @ImmutableEntity annotation.
    1. @Immutable
    2. @Entity
    3. @Table(name = "playground")
*/
@ImmutableEntity(tableName = "playground")
@Value
@NoArgsConstructor(access = PACKAGE, force = true)
@AllArgsConstructor(access = PACKAGE)
public class KeyValue {
    @Id
    @Column(name = "k")
    String key;

    @Column(name = "v")
    String value;
}
