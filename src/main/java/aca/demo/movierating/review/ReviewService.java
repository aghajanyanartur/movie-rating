package aca.demo.movierating.review;

import aca.demo.movierating.movie.MovieNotFoundException;
import aca.demo.movierating.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private static final String EXCEPTION_MESSAGE_REVIEW = "Review not found";

    public Review getById(Long id) {
        log.debug("ReviewService getting review by id - {}", id);
        return reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(EXCEPTION_MESSAGE_REVIEW));
    }

    public List<Review> search(String description, Instant updatedBefore, Instant updatedAfter, Long userId, double ratingHigherThan, double ratingLowerThan) {
        log.debug("ReviewService searching reviews by parameters - description: {}, updatedBefore: {}, updatedAfter: {}, userId: {}, ratingHigherThan: {}, ratingLowerThan: {}",
                description, updatedBefore, updatedAfter, userId, ratingHigherThan, ratingLowerThan);
        return reviewRepository.search(description, updatedBefore, updatedAfter, userId, ratingHigherThan, ratingLowerThan);
    }

    public void create(CreateReview createReview) {
        log.debug("ReviewService creating new review with createReview - {}", createReview);
        if(reviewRepository.findById(createReview.getId()).isPresent()){
            throw new IllegalArgumentException();
        }
        reviewRepository.persist(new Review(createReview));
    }

    public void update(Long id, UpdateReview updateReview) {
        log.debug("ReviewService updating review with updateReview - {}", updateReview);
        reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(EXCEPTION_MESSAGE_REVIEW)).update(updateReview);
    }

    public void delete(Long id) {
        log.debug("ReviewService deleting review by id - {}", id);
        reviewRepository.delete(reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(EXCEPTION_MESSAGE_REVIEW)));
    }
}