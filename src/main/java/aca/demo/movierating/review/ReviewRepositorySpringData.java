package aca.demo.movierating.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface ReviewRepositorySpringData extends JpaRepository<Review, Long> {

    List<Review> findByDescriptionOrUpdatedAtBeforeOrUpdatedAtAfterOrUserIdOrRatingGreaterThanOrRatingLessThan(
            String description, Instant updatedAtBefore, Instant updatedAtAfter, Long userId, double ratingGreaterThan, double ratingLessThan);
}