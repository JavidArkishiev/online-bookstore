package az.practice.bookstore.model.mapper;

import az.practice.bookstore.model.dto.request.AuthorsDto;
import az.practice.bookstore.model.entity.Authors;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Authors mapToAuthorEntity(AuthorsDto book);


    AuthorsDto mapToDto(Authors authors);

    AuthorsDto mapToAuthorDto(Authors authors);
}
