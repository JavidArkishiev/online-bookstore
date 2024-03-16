package az.practice.bookstore.repository;

import az.practice.bookstore.model.dto.response.OrderDetailsWithLocalDate;
import az.practice.bookstore.model.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query("SELECT NEW az.practice.bookstore.model.dto.response.OrderDetailsWithLocalDate(u.firstName, u.lastName, b.title, o.quantity, o.orderHistory, SUM(o.totalPrice)) " +
            "FROM Orders o " +
            "JOIN o.users u " +
            "JOIN o.books b " +
            "WHERE o.id = :id " +
            "GROUP BY u.firstName, u.lastName, b.title, o.quantity, o.orderHistory")
    List<OrderDetailsWithLocalDate> getOrderDetailsById(@Param("id") Long id);

    List<Orders> findByUsersId(Long userId);
}
