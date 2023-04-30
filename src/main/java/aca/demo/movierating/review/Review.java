package aca.demo.movierating.review;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

@ToString
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Review {
    Long id;
    Long movieId;
    Long userId;
    String description;
    double rating;
    Instant createdAt;
    Instant updatedAt;
}