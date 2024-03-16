package az.practice.bookstore.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private String reviewText;
    @Min(value = 0, message = "Rating should not be less than 0")
    @Max(value = 10, message = "Rating should not be greater than 10")
    private int rating;
    @JsonIgnore
    private Long usersId;
    @JsonIgnore
    private Long booksId;

}
