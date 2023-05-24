package aca.demo.movierating.review;

import aca.demo.movierating.movie.MovieNotFoundException;
import aca.demo.movierating.movie.MovieRepositoryJpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private static final String EXCEPTION_MESSAGE_REVIEW = "Review not found";

//    private final ReviewRepository reviewRepository;
    private final MovieRepositoryJpa movieRepositoryJpa;
    private final ReviewRepositorySpringData reviewRepositorySpringData;

    @Transactional(readOnly = true)
    public Review getById(Long id) {
        log.debug("ReviewService getting review by id: {}", id);
//        return reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(EXCEPTION_MESSAGE_REVIEW));
        return reviewRepositorySpringData.findById(id).orElseThrow(() -> new ReviewNotFoundException(EXCEPTION_MESSAGE_REVIEW));
    }

    @Transactional(readOnly = true)
    public List<Review> search(String description, Instant updatedBefore, Instant updatedAfter, Long userId, double ratingHigherThan, double ratingLowerThan) {
        log.debug("ReviewService searching reviews by parameters - description: {}, updatedBefore: {}, updatedAfter: {}, userId: {}, ratingHigherThan: {}, ratingLowerThan: {}",
                description, updatedBefore, updatedAfter, userId, ratingHigherThan, ratingLowerThan);
//        return reviewRepository.search(description, updatedBefore, updatedAfter, userId, ratingHigherThan, ratingLowerThan);
        return reviewRepositorySpringData.findByDescriptionOrUpdatedAtBeforeOrUpdatedAtAfterOrUserIdOrRatingGreaterThanOrRatingLessThan(
                description, updatedBefore, updatedAfter, userId, ratingHigherThan, ratingLowerThan);
    }

    @Transactional
    public void create(CreateReview createReview) {
        log.debug("ReviewService creating new review with createReview - {}", createReview);
//        if(reviewRepository.findById(createReview.getId()).isPresent()){
//            throw new IllegalArgumentException();
//        }
//        var review = new Review(createReview);
//        review.setMovie(movieRepositoryJpa.findById(createReview.getMovieId()).orElseThrow(() -> new MovieNotFoundException("Movie not found")));
//
//        reviewRepository.persist(review);

        if(reviewRepositorySpringData.findById(createReview.getId()).isPresent()) {
            throw new IllegalArgumentException();
        }
        var review = new Review(createReview);
        review.setMovie(movieRepositoryJpa.findById(createReview.getMovieId()).orElseThrow(() -> new MovieNotFoundException("Movie not found")));

        reviewRepositorySpringData.save(review);
    }

    @Transactional
    public void update(Long id, UpdateReview updateReview) {
        log.debug("ReviewService updating review with updateReview - {}", updateReview);
        var review = getById(id);
        review.update(updateReview);
//        reviewRepository.persist(review);
        reviewRepositorySpringData.save(review);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("ReviewService deleting review by id: {}", id);
        var review = getById(id);
//        reviewRepository.delete(review);
        reviewRepositorySpringData.delete(review);
    }
}