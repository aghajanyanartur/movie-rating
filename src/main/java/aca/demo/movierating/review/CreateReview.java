package aca.demo.movierating.review;

import aca.demo.movierating.movie.Movie;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;

@Value
@Builder
@Jacksonized
public class CreateReview {

    Long id;
    Movie movie;
    Long userId;
    String description;
    double rating;
    Instant createdAt;
}