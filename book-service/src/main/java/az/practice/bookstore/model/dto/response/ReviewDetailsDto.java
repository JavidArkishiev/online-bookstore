package az.practice.bookstore.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDetailsDto {
    private String name;
    private String surname;
    private String bookTitle;
    private String reviewText;
    private int rating;
    private String reviewDate;
}
