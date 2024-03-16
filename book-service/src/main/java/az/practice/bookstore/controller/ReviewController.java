package az.practice.bookstore.controller;

import az.practice.bookstore.model.dto.request.ReviewDto;
import az.practice.bookstore.model.dto.response.ReviewDetailsDto;
import az.practice.bookstore.service.ReviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ReviewDto> createReview(@Valid @RequestBody ReviewDto reviewDto,
                                                  @RequestParam Long userId,
                                                  @RequestParam Long bookId) {
        return new ResponseEntity<>(reviewService.cretaReview(reviewDto, userId, bookId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> deleteRatingById(@PathVariable Long id) {
        reviewService.deleteRatingById(id);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<ReviewDetailsDto> getReviewById(@PathVariable Long id) {
        return new ResponseEntity<>(reviewService.getReviewById(id), HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<List<ReviewDetailsDto>> getAllReview() {
        return ResponseEntity.ok(reviewService.getAllReview());
    }


}
