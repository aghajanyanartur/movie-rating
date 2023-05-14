package aca.demo.movierating.review;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class ReviewRepositoryInMemory implements ReviewRepository {

    private List<Review> reviews = new ArrayList<>();

    public Optional<Review> findById(Long id) {
        log.debug("Finding review by reviewId: {}", id);
        return reviews.stream().filter(r -> r.getId().equals(id)).findFirst();
    }

    public List<Review> search(String description, Instant updatedBefore, Instant updatedAfter, Long userId, double ratingHigherThan, double ratingLowerThan) {
        log.debug("Finding reviews by parameters - description: {}, updatedBefore: {}, updatedAfter: {}, userId: {}, ratingHigherThan: {}, ratingLowerThan: {}",
                description, updatedBefore, updatedAfter, userId, ratingHigherThan, ratingLowerThan);
        return reviews.stream()
                .filter(review -> description == null || review.getDescription().equals(description))
                .filter(review -> updatedBefore == null || review.getUpdatedAt().isBefore(updatedBefore))
                .filter(review -> updatedAfter == null || review.getUpdatedAt().isAfter(updatedAfter))
                .filter(review -> userId == null || review.getUserId().equals(userId))
                .filter(review -> ratingHigherThan == 0.0 || Double.compare(review.getRating(), ratingHigherThan) == 1)
                .filter(review -> ratingLowerThan == 0.0 || Double.compare(review.getRating(), ratingLowerThan) == -1)
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