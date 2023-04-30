package aca.demo.movierating.review;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@ToString
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Slf4j
public class Review {

    @EqualsAndHashCode.Include
    Long id;
    Long movieId;
    Long userId;
    String description;
    double rating;
    Instant createdAt;
    Instant updatedAt;

    public Review(CreateReview createReview) {
        log.debug("Creating new review with createReview - {}", createReview);
        this.id = createReview.getId();
        this.movieId = createReview.getMovieId();
        this.userId = createReview.getUserId();
        this.description = createReview.getDescription();
        this.rating = createReview.getRating();
        this.createdAt = createReview.getCreatedAt();
        this.updatedAt = createReview.getCreatedAt();
    }

    public void update(UpdateReview updateReview) {
        log.debug("Updating review with updateReview - {}", updateReview);
        this.userId = updateReview.getUserId();
        this.description = updateReview.getDescription();
        this.rating = updateReview.getRating();
    }
}