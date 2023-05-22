package aca.demo.movierating.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

public interface MovieRepositorySpringData extends JpaRepository<Movie, Long> {

    List<Movie> findByGenreOrTitleOrReleasedAtBeforeOrReleasedAtAfter(Genre genre, String title, LocalDate releasedAtBefore, LocalDate releasedAtAfter);
}