package aca.demo.movierating.movie;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Value
@Builder
@Jacksonized
public class CreateMovie {

    @NotNull
    Long id;

    @NotNull
    @Size(max = 100)
    String title;

    @NotNull
    Genre genre;

    @NotNull
    LocalDate releasedAt;

    @NotNull
    @Size(min = 2, max = 50)
    String director;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
    double rating;
}
