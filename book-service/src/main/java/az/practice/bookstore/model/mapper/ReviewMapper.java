package az.practice.bookstore.model.mapper;

import az.practice.bookstore.model.dto.request.ReviewDto;
import az.practice.bookstore.model.dto.response.ReviewDetailsDto;
import az.practice.bookstore.model.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review mapToReviewEntity(ReviewDto reviewDto);

    @Mapping(target = "name", source = "users.firstName")
    @Mapping(target = "surname", source = "users.lastName")
    @Mapping(target = "bookTitle", source = "book.title")
    ReviewDetailsDto mapToReviewDetailsDto(Review reviewEntity);

    List<ReviewDetailsDto> mapToListReviewDto(List<Review> reviews);
}
