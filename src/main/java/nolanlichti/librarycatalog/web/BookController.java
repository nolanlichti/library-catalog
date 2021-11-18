package nolanlichti.librarycatalog.web;

import nolanlichti.librarycatalog.domain.Book;
import nolanlichti.librarycatalog.repositories.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class BookController {
    private final BookRepository bookRepository;
    private static final List<String> CATEGORIES = List.of("NON-FICTION", "ADULT FICTION", "JUVENILE FICTION", "CHILDREN");

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books/{id}")
    public Book findBook(@PathVariable Integer id) {
        return bookRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/books")
    @PutMapping("/books")
    public Book saveBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Integer id) {
        bookRepository.deleteById(id);
    }

    @GetMapping("/books")
    public List<Book> findBooks(@RequestParam(required = false) String title, @RequestParam(required = false) String author, @RequestParam(required = false) String category) {
        var books = new ArrayList<Book>();
        if (title != null) {
            books.addAll(bookRepository.findBooksByTitleContainsIgnoreCase(title));
        }

        if (author != null) {
            Arrays.stream(author.split(" "))
                    .forEach(name -> books.addAll(bookRepository.findBooksByAuthorFirstNameContainsIgnoreCaseOrAuthorLastNameContainsIgnoreCase(name, name)));
        }

        if (category != null) {
            var validCategory = category.toUpperCase();
            if (!CATEGORIES.contains(validCategory)) {
                validCategory = "OTHER";
            }
            books.addAll(bookRepository.findBooksByCategory(validCategory));
        }
        return books;
    }
}
