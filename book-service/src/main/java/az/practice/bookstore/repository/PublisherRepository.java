package az.practice.bookstore.repository;

import az.practice.bookstore.model.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
