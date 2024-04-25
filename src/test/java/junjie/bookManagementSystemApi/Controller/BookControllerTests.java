package junjie.bookManagementSystemApi.Controller;

import junjie.bookManagementSystemApi.Entity.Book;
import junjie.bookManagementSystemApi.Repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private BookRepository repository;
    @Test
    void addBookShouldReturnAddedBook() {
        var responseBook = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/" + "books",
                        new Book("new book", "somebody", "2024", "1234567890"), Book.class);
        assertThat(responseBook.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseBook.getBody().getId()).isNotNull();
        assertThat(responseBook.getBody().getTitle()).isEqualTo("new book");
        assertThat(responseBook.getBody().getAuthor()).isEqualTo("somebody");
        assertThat(responseBook.getBody().getPublicationYear()).isEqualTo("2024");
        assertThat(responseBook.getBody().getIsbn()).isEqualTo("1234567890");
    }

    @Test
    void allBooksShouldReturnAllBooks() {
        repository.deleteAll();
        repository.save(new Book("new book", "somebody", "2024", "1234567890"));
        var responseBooks = this.restTemplate.getForEntity("http://localhost:" + port + "/" + "books", Book[].class);
        assertThat(responseBooks.getBody().length).isEqualTo(1);
        assertThat(responseBooks.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseBooks.getBody()[0].getId()).isNotNull();
        assertThat(responseBooks.getBody()[0].getTitle()).isEqualTo("new book");
        assertThat(responseBooks.getBody()[0].getAuthor()).isEqualTo("somebody");
        assertThat(responseBooks.getBody()[0].getPublicationYear()).isEqualTo("2024");
        assertThat(responseBooks.getBody()[0].getIsbn()).isEqualTo("1234567890");
    }

    @Test
    void getBookShouldReturnBookWithSpecifiedId() {
        repository.deleteAll();
        var savedBook = repository.save(new Book("new book", "somebody", "2024", "1234567890"));
        var responseBook = this.restTemplate.getForEntity("http://localhost:" + port + "/" + "books" + "/" + savedBook.getId(), Book.class);
        assertThat(responseBook.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseBook.getBody().getId()).isNotNull();
        assertThat(responseBook.getBody().getTitle()).isEqualTo("new book");
        assertThat(responseBook.getBody().getAuthor()).isEqualTo("somebody");
        assertThat(responseBook.getBody().getPublicationYear()).isEqualTo("2024");
        assertThat(responseBook.getBody().getIsbn()).isEqualTo("1234567890");
    }

    @Test
    void updateBookShouldReturnUpdatedBook() {
        repository.deleteAll();
        var savedBook = repository.save(new Book("new book", "somebody", "2024", "1234567890"));
        this.restTemplate.put("http://localhost:" + port + "/" + "books" + "/" + savedBook.getId(),
                new Book("updated book", "somebody else", "2025", "1234567891"));
        var updatedBook = repository.findById(savedBook.getId()).get();
        assertThat(updatedBook.getId()).isNotNull();
        assertThat(updatedBook.getTitle()).isEqualTo("updated book");
        assertThat(updatedBook.getAuthor()).isEqualTo("somebody else");
        assertThat(updatedBook.getPublicationYear()).isEqualTo("2025");
        assertThat(updatedBook.getIsbn()).isEqualTo("1234567891");
    }

    @Test
    void deleteBookShouldReturnOk() {
        repository.deleteAll();
        var savedBook = repository.save(new Book("new book", "somebody", "2024", "1234567890"));
        this.restTemplate.delete("http://localhost:" + port + "/" + "books" + "/" + savedBook.getId());
        var isDeletedBook = repository.findById(savedBook.getId()).isEmpty();
        assertThat(isDeletedBook).isEqualTo(true);
    }
}
