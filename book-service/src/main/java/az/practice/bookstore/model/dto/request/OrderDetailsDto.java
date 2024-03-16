package az.practice.bookstore.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {
    private String name;
    private String surname;
    private String bookTitle;
    private int quantity;
    private Double totalPrice;


}
