package az.practice.bookstore.repository;

import az.practice.bookstore.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsBySerialNumber(String serialNumber);
}
