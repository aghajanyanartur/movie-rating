package aca.demo.movierating.movie;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@ToString
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Slf4j
public class Movie {

    @EqualsAndHashCode.Include
    Long id;

    String title;
    Genre genre;
    LocalDate releasedAt;
    String director;
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
        this.id = updateMovie.getId();
        this.title = updateMovie.getTitle();
        this.genre = updateMovie.getGenre();
        this.releasedAt = updateMovie.getReleasedAt();
        this.director = updateMovie.getDirector();
        this.rating = updateMovie.getRating();
    }
}