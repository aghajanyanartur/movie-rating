package aca.demo.movierating.review;


import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;

@Value
@Builder
@Jacksonized
public class UpdateReview {

    Long userId;
    String description;
    Double rating;
    Instant updatedAt;
}