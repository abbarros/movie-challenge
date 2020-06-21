package br.com.abbarros.moviechallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateMovieException extends RuntimeException {

    public DuplicateMovieException(final String movieName) {
        super(String.format("It was not possible to register because there is a movie with the name %s registered", movieName));
    }

}
