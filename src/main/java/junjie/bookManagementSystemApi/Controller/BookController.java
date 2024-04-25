package junjie.bookManagementSystemApi.Controller;

import junjie.bookManagementSystemApi.Book;
import junjie.bookManagementSystemApi.BookRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}