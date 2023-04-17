package aca.demo.movierating;

import aca.demo.movierating.movie.Genre;
import aca.demo.movierating.movie.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class MovieRatingApplication {

 	public static void main(String[] args) {

		SpringApplication.run(MovieRatingApplication.class, args);
	}
}
