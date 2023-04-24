package aca.demo.movierating.movie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

    public List<Movie> search(Genre genre, String title, LocalDate releasedBefore, LocalDate releasedAfter) {
        log.debug("Finding movies by parameters - genre: {}, title: {}, releasedBefore: {}, releasedAfter: {}",
                genre, title, releasedBefore, releasedAfter);
        return movies.stream()
                .filter(m -> genre == null || m.getGenre() == genre)
                .filter(m -> title == null || m.getTitle().equals(title))
                .filter(m -> releasedBefore == null || m.getReleasedAt().isBefore(releasedBefore))
                .filter(m -> releasedAfter == null || m.getReleasedAt().isAfter(releasedAfter))
                .toList();
    }

    public void persist(Movie movie) {
        log.debug("Adding new movie to movies list with movie - {}", movie);
        movies.add(movie);
    }

    public void delete(Movie movie) {
        log.debug("Deleting movie from list - {}", movie);
        movies.remove(findById(movie.getId()).get()); // Presence of the movie is checked in MovieService
    }
}