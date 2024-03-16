package az.practice.bookstore.repository;

import az.practice.bookstore.model.entity.Address;
import az.practice.bookstore.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Users> findByUsersId(Long userId);
    boolean existsByUsersId(Long id);
}
