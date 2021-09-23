package spring.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripleRepository extends JpaRepository<Triple, String> {
}
