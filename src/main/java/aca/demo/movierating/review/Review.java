package aca.demo.movierating.review;

import aca.demo.movierating.movie.Movie;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@ToString
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Slf4j
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Review {

    @EqualsAndHashCode.Include
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    private Long userId;
    private String description;
    private Double rating;
    private Instant createdAt;
    private Instant updatedAt;

    public Review(CreateReview createReview) {
        log.debug("Creating new review with createReview - {}", createReview);
        this.id = createReview.getId();
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
        this.updatedAt = updateReview.getUpdatedAt();
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}