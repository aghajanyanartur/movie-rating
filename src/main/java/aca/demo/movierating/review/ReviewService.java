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
    private final MovieRepository movieRepository;
    private static final String EXCEPTION_MESSAGE_REVIEW = "Review not found";
    private static final String EXCEPTION_MESSAGE_MOVIE = "Movie not found";

    public Review getById(Long movieId, Long reviewId) {
        log.debug("ReviewService getting review by - movieId: {}, and reviewId: {}", reviewId);
        if(movieRepository.findById(movieId).isEmpty()){
            throw new MovieNotFoundException(EXCEPTION_MESSAGE_MOVIE);
        }
        return reviewRepository.findById(movieId, reviewId).orElseThrow(() -> new ReviewNotFoundException(EXCEPTION_MESSAGE_REVIEW));
    }

    public List<Review> search(String description, Instant updatedBefore, Instant updatedAfter, Long userId, double ratingHigherThan, double ratingLowerThan) {
        log.debug("ReviewService searching reviews by parameters - description: {}, updatedBefore: {}, updatedAfter: {}, userId: {}, ratingHigherThan: {}, ratingLowerThan: {}",
                description, updatedBefore, updatedAfter, userId, ratingHigherThan, ratingLowerThan);
        return reviewRepository.search(description, updatedBefore, updatedAfter, userId, ratingHigherThan, ratingLowerThan);
    }

    public void create(Long movieId, CreateReview createReview) {
        log.debug("ReviewService creating new review with createReview - {}", createReview);
        if(reviewRepository.findById(movieId, createReview.getId()).isPresent()){
            throw new IllegalArgumentException();
        }
        reviewRepository.persist(new Review(createReview));
    }

    public void update(Long movieId, Long id, UpdateReview updateReview) {
        log.debug("ReviewService updating review with updateReview - {}", updateReview);
        reviewRepository.findById(movieId, id).orElseThrow(() -> new ReviewNotFoundException(EXCEPTION_MESSAGE_REVIEW)).update(updateReview);
    }

    public void delete(Long movieId, Long id) {
        log.debug("ReviewService deleting review by - movieId {}, and id: {}", movieId, id);
        reviewRepository.delete(movieId, reviewRepository.findById(movieId, id).orElseThrow(() -> new ReviewNotFoundException(EXCEPTION_MESSAGE_REVIEW)));
    }
}