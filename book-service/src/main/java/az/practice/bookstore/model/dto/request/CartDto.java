package az.practice.bookstore.model.dto.request;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    @PositiveOrZero(message = "Quantity must be a non-negative value")
    private int quantity;

}
