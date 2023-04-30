package aca.demo.movierating.review;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class ReviewRepository {

    private List<Review> reviews = new ArrayList<>();

    public Optional<Review> findById(Long id) {
        log.debug("Finding review by id - {}", id);
        return reviews.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    public List<Review> search(Long movieId, Long userId) {
        log.debug("Finding reviews by parameters - movieId: {}, userId: {}", movieId, userId);
        return reviews.stream()
                .filter(r -> movieId == null || r.getMovieId().equals(movieId))
                .filter(r -> userId == null || r.getUserId().equals(userId))
                .toList();
    }

    public void persist(Review review) {
        log.debug("Adding new review to reviews list - {}", review);
        reviews.add(review);
    }

    public void delete(Review review) {
        log.debug("Deleting review from list - {}", review);
        reviews.remove(findById(review.getId()).get()); // Presence of the movie is checked in ReviewService
    }
}