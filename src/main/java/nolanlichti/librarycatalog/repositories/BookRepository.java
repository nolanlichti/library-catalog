package nolanlichti.librarycatalog.repositories;

import nolanlichti.librarycatalog.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    List<Book> findBooksByAuthorFirstNameContainsIgnoreCaseOrAuthorLastNameContainsIgnoreCase(String firstName, String lastName);

    List<Book> findBooksByTitleContainsIgnoreCase(String title);

    List<Book> findBooksByCategory(String category);
}
