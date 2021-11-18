package nolanlichti.librarycatalog.web;

import nolanlichti.librarycatalog.domain.Patron;
import nolanlichti.librarycatalog.repositories.BookRepository;
import nolanlichti.librarycatalog.repositories.PatronRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class PatronController {
    private final PatronRepository patronRepository;
    private final BookRepository bookRepository;

    public PatronController(PatronRepository patronRepository, BookRepository bookRepository) {
        this.patronRepository = patronRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/patrons/{id}")
    public Patron getPatron(@PathVariable String id) {
        return patronRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/books")
    @PutMapping("/books")
    public Patron savePatron(@RequestBody Patron patron) {
        return patronRepository.save(patron);
    }

    @DeleteMapping("/books/{id}")
    public void deletePatron(@PathVariable String id) {
        patronRepository.deleteById(id);
    }

    @GetMapping("/patrons/{id}/checkout/{bookId}")
    public String checkoutBook(@PathVariable String id, @PathVariable String bookId) {
        var patron = patronRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Patron not found"));
        var book = bookRepository.findById(bookId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Book not found"));
        var dueDate = patron.checkOutItem(book);
        patronRepository.save(patron);
        return dueDate;
    }

    @GetMapping("/patrons/{id}/return/{bookId}")
    public double returnBook(@PathVariable String id, @PathVariable String bookId) {
        var patron = patronRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Patron not found"));
        var book = bookRepository.findById(bookId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Book not found"));
        var fines = patron.returnItem(book);
        patronRepository.save(patron);
        return fines;
    }
}
