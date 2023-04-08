package aca.demo.movierating.movie;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@Getter
@EqualsAndHashCode
@Slf4j
public class Movie {

    String title;
    Genre genre;

    public Movie(CreateMovie createMovie) {
        log.debug("Constructing movie with createMovie - {}", createMovie);
        this.title = createMovie.getTitle();
        this.genre = createMovie.getGenre();
    }
}