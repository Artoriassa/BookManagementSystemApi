package junjie.bookManagementSystemApi.Controller;

import junjie.bookManagementSystemApi.Book;
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

    @Test
    void addBookShouldReturnAddedBook() throws Exception {
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
}
