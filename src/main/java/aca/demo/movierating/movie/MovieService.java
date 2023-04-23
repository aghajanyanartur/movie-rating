package aca.demo.movierating.movie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private static final String EXCEPTION_MESSAGE = "Movie not found";

    public Movie getById(Long id) {
        log.debug("MovieService getting movie by id - {}", id);
        var movie = movieRepository.findById(id);

        if(movie.isPresent()){
            return movie.get();
        } else {
            throw new MovieNotFoundException(EXCEPTION_MESSAGE);
        }
    }

    public List<Movie> search(Genre genre, String title, LocalDate releasedBefore, LocalDate releasedAfter) {
        log.debug("MovieService searching movies by parameters - genre: {}, title: {}, releasedBefore: {}, releasedAfter: {}",
                genre, title, releasedBefore, releasedAfter);
        return movieRepository.search(genre, title, releasedBefore, releasedAfter);
    }

    public void create(CreateMovie createMovie) {
        log.debug("MovieService creating new movie with createMovie - {}", createMovie);
        if(movieRepository.findById(createMovie.getId()).isPresent()){
            throw new IllegalArgumentException();
        }
        movieRepository.persist(new Movie(createMovie));
    }

    public void update(Long id, UpdateMovie updateMovie) {
        log.debug("MovieService updating movie with updateMovie - {}", updateMovie);
        var movie = movieRepository.findById(id);

        if(movie.isPresent()){
            movie.get().update(updateMovie);
        } else {
            throw new MovieNotFoundException(EXCEPTION_MESSAGE);
        }
    }
    public void delete(Long id) {
        log.debug("MovieService deleting movie by id - {}", id);
        var movie = movieRepository.findById(id);

        if(movie.isPresent()){
            movieRepository.delete(movie.get());
        } else {
            throw new MovieNotFoundException(EXCEPTION_MESSAGE);
        }
    }
}