package aca.demo.movierating.endpoint;

import lombok.Value;

@Value
public class ErrorResponse {
    int errorCode;
    String description;
    String field;
}