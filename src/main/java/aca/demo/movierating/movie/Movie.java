package aca.demo.movierating.movie;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@ToString
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Slf4j
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Movie {

    @EqualsAndHashCode.Include
    @Id
    Long id;

    @NotNull
    @Size(max = 100)
    String title;

    @NotNull
    @Enumerated(EnumType.STRING)
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

    public Movie(CreateMovie createMovie) {
        log.debug("Constructing movie with createMovie - {}", createMovie);
        this.id = createMovie.getId();
        this.title = createMovie.getTitle();
        this.genre = createMovie.getGenre();
        this.releasedAt = createMovie.getReleasedAt();
        this.director = createMovie.getDirector();
        this.rating = createMovie.getRating();
    }

    public void update(UpdateMovie updateMovie) {
        log.debug("Updating movie with updateMovie - {}", updateMovie);
        this.title = updateMovie.getTitle();
        this.genre = updateMovie.getGenre();
        this.releasedAt = updateMovie.getReleasedAt();
        this.director = updateMovie.getDirector();
        this.rating = updateMovie.getRating();
    }
}