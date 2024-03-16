package az.practice.bookstore.model.mapper;

import az.practice.bookstore.model.dto.request.BookDto;
import az.practice.bookstore.model.dto.response.CartItemResponseDto;
import az.practice.bookstore.model.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book mapToBookEntity(BookDto book);

    BookDto mapToBookDto(Book books);

    CartItemResponseDto CartItemResponseDto(Book books);
}
