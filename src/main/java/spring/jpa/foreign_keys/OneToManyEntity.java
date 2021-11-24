package spring.jpa.foreign_keys;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity
@Table(name = "one2many")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
class OneToManyEntity extends AuditedEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @EqualsAndHashCode.Include
    Integer id;

    @OneToMany(mappedBy = "parent", cascade = ALL, orphanRemoval = true)
    List<ManyToOneEntity> children;
}
