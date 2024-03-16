package az.practice.bookstore.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorsDto {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirthday;
    private String biography;

}
