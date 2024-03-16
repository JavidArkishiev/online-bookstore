package az.practice.bookstore.service;

import az.practice.bookstore.exception.BookNotFoundException;
import az.practice.bookstore.exception.ReviewNotFound;
import az.practice.bookstore.exception.UserNotFoundException;
import az.practice.bookstore.model.dto.request.ReviewDto;
import az.practice.bookstore.model.dto.response.ReviewDetailsDto;
import az.practice.bookstore.model.entity.Book;
import az.practice.bookstore.model.entity.Review;
import az.practice.bookstore.model.entity.Users;
import az.practice.bookstore.model.mapper.ReviewMapper;
import az.practice.bookstore.repository.BookRepository;
import az.practice.bookstore.repository.ReviewRepository;
import az.practice.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;


    public ReviewDto cretaReview(ReviewDto reviewDto, Long userId, Long bookId) {
        Review reviewEntity = reviewMapper.mapToReviewEntity(reviewDto);
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("this id not found :" + userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("this id not found :" + bookId));

        reviewEntity.setUsers(users);
        reviewEntity.setBook(book);
        reviewRepository.save(reviewEntity);
        return reviewDto;


    }

    public void deleteRatingById(Long id) {
        Review reviewEntity = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFound("this id not found :" + id));
        reviewRepository.delete(reviewEntity);
    }

    public ReviewDetailsDto getReviewById(Long id) {
        Review reviewEntity = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFound("this id not found :" + id));
//        UserDto userDto = userMapper.mapToUserDto(reviewEntity.getUsers());
        ReviewDetailsDto reviewDetailsDto = reviewMapper.mapToReviewDetailsDto(reviewEntity);
        return reviewDetailsDto;

    }

    public List<ReviewDetailsDto> getAllReview() {
        List<Review> reviews = reviewRepository.findAll();
        return reviewMapper.mapToListReviewDto(reviews);

    }
}
