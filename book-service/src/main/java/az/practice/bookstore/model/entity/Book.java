package az.practice.bookstore.model.entity;

import az.practice.bookstore.enums.CategoryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String title;
    private Double price;
    private CategoryType categoryType;
    private String publisher;
    private int stockQuantity;
    private LocalDate publicationDate;
    @Column(unique = true)
    private String serialNumber;
    private String description;
    private String bookCover;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authors_id", referencedColumnName = "id")
    private Authors authors;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    private Publisher publishers;

    @OneToMany(mappedBy = "book")
    private List<Review> review;

    @OneToMany(mappedBy = "books")
    private List<Orders> orders;

    @ManyToOne()
    @JoinColumn(name = "carts_id", referencedColumnName = "id")
    private Cart carts;
    @OneToMany(mappedBy = "books")
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "books")
    private List<CartItem> cartItems;


}
