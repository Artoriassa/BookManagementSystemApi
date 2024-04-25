package junjie.bookManagementSystemApi.Repository;

import junjie.bookManagementSystemApi.Entity.Book;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByIsbnContaining(String isbn, Sort sort);
}