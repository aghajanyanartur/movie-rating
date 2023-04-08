package aca.demo.movierating.movie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class MovieRepository {

    private List<Movie> movies = new ArrayList<>();

    public Optional<Movie> findByTitle(String title) {
        log.debug("Finding movie by title - {}", title);
        return movies.stream().filter(m -> m.getTitle().equals(title)).findFirst();
    }

    public List<Movie> findByGenre(Genre genre) {
        log.debug("Finding movies by title - {}", genre);
        return movies.stream().filter(m -> m.getGenre() == genre).toList();
    }

    public void save(CreateMovie createMovie) {
        log.debug("Adding new movie to movies list with createMovie - {}", createMovie);
        movies.add(new Movie(createMovie));
    }
}