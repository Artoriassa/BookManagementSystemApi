package junjie.bookManagementSystemApi.Controller;

import junjie.bookManagementSystemApi.Entity.Book;
import junjie.bookManagementSystemApi.Repository.BookRepository;
import junjie.bookManagementSystemApi.Exception.BookNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookRepository repository;

    BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/books")
    List<Book> allBooks() {
        Sort sort = Sort.by(Sort.Direction.ASC, "title");
        return repository.findAll(sort);
    }

    @PostMapping("/books")
    Book addBook(@RequestBody Book newBook) {
        return repository.save(newBook);
    }
    @GetMapping("/isbn/{isbn}")
    List<Book> getBooksByIsbn(@PathVariable String isbn) {
        Sort sort = Sort.by(Sort.Direction.ASC, "title");
        return repository.findByIsbnContaining(isbn, sort);
    }

    @GetMapping("/books/{id}")
    Book getBook(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @PutMapping("/books/{id}")
    Book updateBook(@RequestBody Book revisedBook, @PathVariable Long id) {
        return repository.findById(id)
                .map(book -> {
                    book.setTitle(revisedBook.getTitle());
                    book.setAuthor(revisedBook.getAuthor());
                    book.setPublicationYear(revisedBook.getPublicationYear());
                    book.setIsbn(revisedBook.getIsbn());
                    return repository.save(book);
                }).get();
    }

    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
    }
}