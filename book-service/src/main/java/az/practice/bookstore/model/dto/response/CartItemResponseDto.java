package az.practice.bookstore.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDto {
    private int quantity;
    private double totalPrice;
    private String author;
    private String title;
}
