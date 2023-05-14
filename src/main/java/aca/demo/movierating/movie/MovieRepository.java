package aca.demo.movierating.movie;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    Optional<Movie> findById(Long id);

    void persist(Movie movie);

    void delete(Movie movie);

    List<Movie> search(Genre genre, String title, LocalDate releasedBefore, LocalDate releasedAfter);
}
