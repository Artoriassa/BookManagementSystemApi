package junjie.bookManagementSystemApi.Controller;

import junjie.bookManagementSystemApi.Book;
import junjie.bookManagementSystemApi.BookRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final BookRepository repository;

    BookController(BookRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/books")
    Book addBook(@RequestBody Book newBook) {
        return repository.save(newBook);
    }
}