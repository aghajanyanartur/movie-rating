package aca.demo.movierating.endpoint;

import aca.demo.movierating.movie.MovieNotFoundException;
import aca.demo.movierating.review.ReviewNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(MovieNotFoundException movieNotFoundException) {
        log.error("Exception - {}", movieNotFoundException);
        return ResponseEntity.status(400).body(new ErrorResponse(1001, movieNotFoundException.getMessage(), null));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(ReviewNotFoundException reviewNotFoundException) {
        log.error("Exception - {}", reviewNotFoundException);
        return ResponseEntity.status(400).body(new ErrorResponse(1003, reviewNotFoundException.getMessage(), null));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(IllegalArgumentException illegalArgumentException) {
        log.error("Exception - {}", illegalArgumentException);
        return ResponseEntity.status(400).body(new ErrorResponse(1002, illegalArgumentException.getMessage(), null));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(RuntimeException runtimeException) {
        log.error("Exception - {}", runtimeException);
        return ResponseEntity.status(400).body(new ErrorResponse(1100, runtimeException.getMessage(), null));
    }
}