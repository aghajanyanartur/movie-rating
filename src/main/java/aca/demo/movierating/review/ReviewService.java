package aca.demo.movierating.review;

import aca.demo.movierating.movie.MovieNotFoundException;
import aca.demo.movierating.movie.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private static final String EXCEPTION_MESSAGE_REVIEW = "Review not found";
    private static final String EXCEPTION_MESSAGE_MOVIE = "Movie not found";

    public Review getById(Long id) {
        log.debug("ReviewService getting review by id - {}", id);
        return reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(EXCEPTION_MESSAGE_REVIEW));
    }

    public List<Review> search(Long movieId, Long userId) {
        log.debug("ReviewService searching reviews by parameters - movieId: {}, userId: {}", movieId, userId);
        if(movieRepository.findById(movieId).isEmpty()) {
            throw new MovieNotFoundException(EXCEPTION_MESSAGE_MOVIE);
        }
        return reviewRepository.search(movieId, userId);
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