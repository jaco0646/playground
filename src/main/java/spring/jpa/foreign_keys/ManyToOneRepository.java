package spring.jpa.foreign_keys;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManyToOneRepository extends CrudRepository<ManyToOneEntity, Integer> {
}
