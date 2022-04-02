package spring.transaction;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "my_table")
public class MyEntity {
    @Id
    @GeneratedValue
    int id;

    @Column
    String text;
}
