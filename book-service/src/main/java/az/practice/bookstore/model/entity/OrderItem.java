package az.practice.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private double totalPrice;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "books_id", referencedColumnName = "id")
    private Book books;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orders_id", referencedColumnName = "id")
    private Orders orders;


}
