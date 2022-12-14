package spring.envers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface PersonRepository extends JpaRepository<Person, Integer>, RevisionRepository<Person, Integer, Integer> {
}
