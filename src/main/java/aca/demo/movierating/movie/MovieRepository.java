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

    public Optional<Movie> findById(Long id) {
        log.debug("Finding movie by id - {}", id);
        return movies.stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    public List<Movie> findByGenre(Genre genre) {
        log.debug("Finding movies by title - {}", genre);
        return movies.stream().filter(m -> m.getGenre() == genre).toList();
    }

    public void persist(Movie movie) {
        log.debug("Adding new movie to movies list with movie - {}", movie);
        movies.add(movie);
    }

    public void delete(Movie movie) {
        log.debug("Deleting movie from list - {}", movie);
        movies.remove(findById(movie.getId()).get());
    }
}