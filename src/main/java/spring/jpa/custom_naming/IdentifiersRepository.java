package spring.jpa.custom_naming;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IdentifiersRepository extends JpaRepository<Identifiers, Integer> {

    @Query(value = "SELECT column_name FROM information_schema.columns WHERE table_name = 'identifiers'", nativeQuery = true)
    List<String> getColumnNames();

}
