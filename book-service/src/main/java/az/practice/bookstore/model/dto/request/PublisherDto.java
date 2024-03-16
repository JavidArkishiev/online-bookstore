package az.practice.bookstore.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDto {

    private String country;
    private String publisherName;
    private LocalDate yearOfEstablishment;

}
