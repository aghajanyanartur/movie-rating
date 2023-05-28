package aca.demo.movierating.review;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;

@Value
@Builder
@Jacksonized
public class UpdateReview {

    @NotNull
    Long userId;

    @NotNull
    @Size(max = 1000)
    String description;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
    Double rating;

    @NotNull
    Instant updatedAt;
}