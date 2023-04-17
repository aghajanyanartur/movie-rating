package aca.demo.movierating.endpoint;

import aca.demo.movierating.movie.CreateMovie;
import aca.demo.movierating.movie.Genre;
import aca.demo.movierating.movie.Movie;
import aca.demo.movierating.movie.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping("movies")
    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        String genre = request.getParameter("genre").toUpperCase();
        log.info("Title - {}, genre - {}", title, genre);

        movieService.create(new CreateMovie(title, Genre.valueOf(genre)));

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.getWriter().println("New Movie Created");
    }

    @GetMapping("movies")
    public void search(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String genre = request.getParameter("genre").toUpperCase();
        log.info("Genre - {}", genre);

        var searchResults = movieService.search(Genre.valueOf(genre));

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        for(Movie movie : searchResults) {
            response.getWriter().println(movie);
        }
    }
}