package br.com.abbarros.moviechallenge.controller.mappers;

import br.com.abbarros.moviechallenge.controller.response.MovieResponse;
import br.com.abbarros.moviechallenge.model.Movie;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieResponseMapper {

    private final MapperFacade mapperFacade;

    public MovieResponse map(final Movie movie) {
        return mapperFacade.map(movie, MovieResponse.class);
    }

}
