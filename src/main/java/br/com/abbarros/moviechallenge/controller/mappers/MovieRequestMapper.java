package br.com.abbarros.moviechallenge.controller.mappers;

import br.com.abbarros.moviechallenge.controller.request.MovieRequest;
import br.com.abbarros.moviechallenge.model.Movie;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieRequestMapper {

    private final MapperFacade mapperFacade;

    public Movie map(final MovieRequest movieRequest) {
        return mapperFacade.map(movieRequest, Movie.class);
    }

}
