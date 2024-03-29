package spring.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripleRepository extends CrudRepository<Triple, String> {
}
