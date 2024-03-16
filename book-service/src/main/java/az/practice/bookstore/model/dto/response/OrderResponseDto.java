package az.practice.bookstore.model.dto.response;

import az.practice.bookstore.model.dto.request.BookDto;
import az.practice.bookstore.model.dto.request.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private UserDto userDto;
    private BookDto bookDto;

}
