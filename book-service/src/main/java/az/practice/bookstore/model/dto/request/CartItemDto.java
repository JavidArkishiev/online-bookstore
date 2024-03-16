package az.practice.bookstore.model.dto.request;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    @PositiveOrZero(message = "Quantity must be a non-negative value")
    private int quantity;

}
