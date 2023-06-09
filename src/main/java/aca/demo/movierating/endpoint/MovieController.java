package aca.demo.movierating.endpoint;

import aca.demo.movierating.movie.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/movies/{id}")
    @PreAuthorize("hasAuthority('USER_AUTHORITY')")
    public Movie getById(@PathVariable Long id) {
        log.debug("Endpoint getting movie by path variable id - {}", id);
        return movieService.getById(id);
    }

    @PostMapping("/movies")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity create(@RequestBody @Valid CreateMovie createMovie) {
        log.debug("Endpoint creating a new movie - {}", createMovie);
        movieService.create(createMovie);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PutMapping("/movies/{id}")
    @PreAuthorize("hasRole('USER')")
    public void update(@PathVariable Long id, @RequestBody @Valid UpdateMovie updateMovie) {
        log.debug("Endpoint updating a movie using path variable id - {}, and updateMovie - {}", id, updateMovie);
        movieService.update(id, updateMovie);
    }

    @DeleteMapping("/movies/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        log.debug("Endpoint deleting a movie using path variable id - {}", id);
        movieService.delete(id);
    }

    @GetMapping("/movies")
    @PreAuthorize("hasRole('USER')")
    List<Movie> search(@RequestParam(required = false) Genre genre,
                       @RequestParam(required = false) String title,
                       @RequestParam(required = false) LocalDate releasedBefore,
                       @RequestParam(required = false) LocalDate releasedAfter) {

        log.debug("Endpoint searching for a movie using parameters - genre: {}, title: {}, releasedBefore: {}," +
                        "releasedAfter: {}", genre, title, releasedBefore, releasedAfter);
        return movieService.search(genre, title, releasedBefore, releasedAfter);
    }
}