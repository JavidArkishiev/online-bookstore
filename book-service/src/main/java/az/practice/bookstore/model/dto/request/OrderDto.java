package az.practice.bookstore.model.dto.request;

import az.practice.bookstore.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    @PositiveOrZero(message = "Quantity must be a non-negative value")
    private int quantity;
    @JsonIgnore
    private Long usersId;
    @JsonIgnore
    private Long booksId;
    private OrderStatus orderStatus;


}
