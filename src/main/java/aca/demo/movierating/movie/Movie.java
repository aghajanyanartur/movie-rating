package aca.demo.movierating.movie;

import aca.demo.movierating.review.Review;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@ToString
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Slf4j
@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Movie {

    @EqualsAndHashCode.Include
    @Id
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private LocalDate releasedAt;
    private String director;
    private double rating;

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews;

    public Movie(CreateMovie createMovie) {
        log.debug("Constructing movie with createMovie - {}", createMovie);
        this.id = createMovie.getId();
        this.title = createMovie.getTitle();
        this.genre = createMovie.getGenre();
        this.releasedAt = createMovie.getReleasedAt();
        this.director = createMovie.getDirector();
        this.rating = createMovie.getRating();
    }

    public void update(UpdateMovie updateMovie) {
        log.debug("Updating movie with updateMovie - {}", updateMovie);
        this.title = updateMovie.getTitle();
        this.genre = updateMovie.getGenre();
        this.releasedAt = updateMovie.getReleasedAt();
        this.director = updateMovie.getDirector();
        this.rating = updateMovie.getRating();
    }
}