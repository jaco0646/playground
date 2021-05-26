package spring.jpa;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static lombok.AccessLevel.PACKAGE;

@Immutable
@Value
@NoArgsConstructor(access = PACKAGE, force = true)
@AllArgsConstructor(access = PACKAGE)
@Entity
@Table(name = "playground")
public class KeyValue {
    @Id
    @Column(name = "k")
    String key;

    @Column(name = "v")
    String value;
}
