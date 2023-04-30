package aca.demo.movierating.endpoint;

import aca.demo.movierating.review.CreateReview;
import aca.demo.movierating.review.Review;
import aca.demo.movierating.review.ReviewService;
import aca.demo.movierating.review.UpdateReview;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/movies/{movieId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public Review getById(@PathVariable Long movieId, @PathVariable Long id) {
        log.debug("Endpoint getting the review by path variables - movieId: {}, and reviewId: {}", movieId, id);
        return reviewService.getById(movieId, id);
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@PathVariable Long movieId, @RequestBody CreateReview createReview) {
        log.debug("Endpoint creating new review with createReview - {}", createReview);
        reviewService.create(movieId, createReview);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping("/")
    public void update(@PathVariable Long movieId, @PathVariable Long id, @RequestBody UpdateReview updateReview) {
        log.debug("Endpoint updating a review using path variables - movieId: {} and reviewId: {}, and updateReview: {}", movieId, id, updateReview);
        reviewService.update(movieId, id, updateReview);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long movieId, @PathVariable Long id) {
        log.debug("Endpoint deleting the review with path variables - movieId: {} and reviewId: {}", movieId, id);
        reviewService.delete(movieId, id);
    }

    @GetMapping("/")
    public List<Review> search(@RequestParam(required = false) String description,
                               @RequestParam(required = false) Instant updatedBefore,
                               @RequestParam(required = false) Instant updatedAfter,
                               @RequestParam(required = false) Long userId,
                               @RequestParam(required = false) double ratingHigherThan,
                               @RequestParam(required = false) double ratingLowerThan) {
        log.debug("Endpoint searching reviews by parameters - description: {}, updatedBefore: {}, updatedAfter: {}, userId: {}, ratingHigherThan: {}, ratingLowerThan: {}",
                description, updatedBefore, updatedAfter, userId, ratingHigherThan, ratingLowerThan);
        return reviewService.search(description, updatedBefore, updatedAfter, userId, ratingHigherThan, ratingLowerThan);
    }
}