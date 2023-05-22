package aca.demo.movierating.movie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MovieService {

    private static final String EXCEPTION_MESSAGE = "Movie not found";

//    private final MovieRepository movieRepository;
    private final MovieRepositorySpringData movieRepositorySpringData;

    @Transactional(readOnly = true)
    public Movie getById(Long id) {
        log.debug("MovieService getting movie by id - {}", id);
//        return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(EXCEPTION_MESSAGE));
        return movieRepositorySpringData.findById(id).orElseThrow(() -> new MovieNotFoundException(EXCEPTION_MESSAGE));
    }

    @Transactional(readOnly = true)
    public List<Movie> search(Genre genre, String title, LocalDate releasedBefore, LocalDate releasedAfter) {
        log.debug("MovieService searching movies by parameters - genre: {}, title: {}, releasedBefore: {}, releasedAfter: {}",
                genre, title, releasedBefore, releasedAfter);
//        return movieRepository.search(genre, title, releasedBefore, releasedAfter);
        return movieRepositorySpringData.findByGenreOrTitleOrReleasedAtBeforeOrReleasedAtAfter(genre, title, releasedBefore, releasedAfter);
    }

    @Transactional
    public void create(CreateMovie createMovie) {
        log.debug("MovieService creating new movie with createMovie - {}", createMovie);
//        if(movieRepository.findById(createMovie.getId()).isPresent()){
//            throw new IllegalArgumentException();
//        }
//        movieRepository.persist(new Movie(createMovie));

        if(movieRepositorySpringData.findById(createMovie.getId()).isPresent()) {
            throw new IllegalArgumentException();
        }
        movieRepositorySpringData.save(new Movie(createMovie));
    }

    @Transactional
    public void update(Long id, UpdateMovie updateMovie) {
        log.debug("MovieService updating movie with updateMovie - {}", updateMovie);
        var movie = getById(id);
        movie.update(updateMovie);
//        movieRepository.persist(movie);
        movieRepositorySpringData.save(movie);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("MovieService deleting movie by id - {}", id);
        var movie = getById(id);
//        movieRepository.delete(movie);
        movieRepositorySpringData.delete(movie);
    }
}