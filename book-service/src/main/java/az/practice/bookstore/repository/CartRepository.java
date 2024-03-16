package az.practice.bookstore.repository;

import az.practice.bookstore.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {


    Optional<Cart> findByUsersId(Long userId);
    Optional<Cart> deleteByUsersId(Long userId);


}

