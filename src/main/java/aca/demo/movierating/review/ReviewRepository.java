package aca.demo.movierating.review;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository {

    Optional<Review> findById(Long movieId, Long id);

    List<Review> search(String description, Instant updatedBefore, Instant updatedAfter, Long userId, double ratingHigherThan, double ratingLowerThan);

    void persist(Review review);

    void delete(Long movieId, Review review);
}