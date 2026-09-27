package spring.jpa.custom_naming;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Entity
@Table(name = "identifiers")
@AllArgsConstructor
@NoArgsConstructor
public class Identifiers {
    @Id
    int id;

    @Column
    int camelId;

    @Column
    int bigID;
}
