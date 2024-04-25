package junjie.bookManagementSystemApi.Controller;

import junjie.bookManagementSystemApi.Book;
import junjie.bookManagementSystemApi.BookRepository;
import junjie.bookManagementSystemApi.Exception.BookNotFoundException;
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
        return repository.findAll();
    }

    @PostMapping("/books")
    Book addBook(@RequestBody Book newBook) {
        return repository.save(newBook);
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
}