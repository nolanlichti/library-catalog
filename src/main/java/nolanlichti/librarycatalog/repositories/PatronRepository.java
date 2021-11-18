package nolanlichti.librarycatalog.repositories;

import nolanlichti.librarycatalog.domain.Patron;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepository extends CrudRepository<Patron, String> {
}
