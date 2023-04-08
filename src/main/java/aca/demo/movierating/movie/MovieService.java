package aca.demo.movierating.movie;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<Movie> search(Genre genre) {
        log.debug("Searching by genre - {}", genre);
        return movieRepository.findByGenre(genre);
    }

    public void create(CreateMovie createMovie) {
        log.debug("Creating new movie with createMovie - {}", createMovie);
        if(movieRepository.findByTitle(createMovie.getTitle()).isPresent()){
            throw new IllegalArgumentException();
        }
        movieRepository.save(createMovie);
    }
}
