package az.practice.bookstore.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsWithLocalDate {
    private String name;
    private String surname;
    private String bookTitle;
    private int quantity;
    private LocalDateTime orderHistory;
    private Double totalPrice;

}
