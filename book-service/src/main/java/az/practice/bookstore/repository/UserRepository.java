package az.practice.bookstore.repository;

import az.practice.bookstore.enums.Role;
import az.practice.bookstore.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {


    boolean existsByEmail(String email);



    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Users> findByEmail(String email);

    Users findByRole(Role role);

    Optional<Users> findByOtp(String otp);

}
