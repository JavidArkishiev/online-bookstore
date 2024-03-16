package az.practice.bookstore.model.dto.request;

import az.practice.bookstore.enums.CategoryType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDto {
    private String author;
    @NotBlank(message = "can not be null")
    private String title;
    private Double price;
    private CategoryType categoryType;
    private String publisher;
    private int stockQuantity;
    private LocalDate publicationDate;
    private String serialNumber;
    private String description;
    private AuthorsDto authorsDto;
    private PublisherDto publisherDto;

}
