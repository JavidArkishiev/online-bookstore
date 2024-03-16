package az.practice.bookstore.model.entity;

import az.practice.bookstore.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private Double totalPrice;
    private LocalDateTime orderHistory = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private Users users;

    @ManyToOne()
    @JoinColumn(name = "books_id", referencedColumnName = "id")
    private Book books;

    @OneToMany(mappedBy = "orders" , cascade ={ CascadeType.PERSIST,CascadeType.REMOVE} )
    private List<OrderItem> orderItems = new ArrayList<>();

}
