package spring.envers;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Audited
@Table(name = "person")
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    Integer id;

//    A Version field may be used to prevent stale data from being persisted.
//      It enables optimistic locking.
//    @Version
//    Integer version;

    @Column
    String firstName;

    @Column
    String lastName;
}
