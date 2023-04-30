package aca.demo.movierating.endpoint;

import aca.demo.movierating.review.CreateReview;
import aca.demo.movierating.review.Review;
import aca.demo.movierating.review.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/movies/{movieId}")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews/{id}")
    public Review getById(@PathVariable Long id) {
        log.debug("Endpoint getting the review by id - {}", id);
        return reviewService.getById(id);
    }

    @PostMapping("/reviews")
    public ResponseEntity<Void> create(@RequestBody CreateReview createReview) {
        log.debug("Endpoint creating new review with createReview - {}", createReview);
        reviewService.create(createReview);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
