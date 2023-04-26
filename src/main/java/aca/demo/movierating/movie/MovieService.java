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
        return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(EXCEPTION_MESSAGE));
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
        movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(EXCEPTION_MESSAGE)).update(updateMovie);
    }
    public void delete(Long id) {
        log.debug("MovieService deleting movie by id - {}", id);
        movieRepository.delete(movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(EXCEPTION_MESSAGE)));
    }
}