package br.com.abbarros.moviechallenge.controller.mappers;

import br.com.abbarros.moviechallenge.controller.request.MovieRequest;
import br.com.abbarros.moviechallenge.controller.response.MovieResponse;
import br.com.abbarros.moviechallenge.model.Movie;
import br.com.abbarros.moviechallenge.repository.MovieRepository;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static br.com.abbarros.moviechallenge.templates.Templates.BASE_PACKAGE;
import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class MovieRequestMapperTest {

    private static final String MOVIE = "movieValid";
    private static final String MOVIE_REQUEST = "movieRequestValid";

    @InjectMocks
    private MovieRequestMapper mapper;

    @Mock
    private MapperFacade mapperFacade;

    @BeforeAll
    public static void beforeClass() {
        loadTemplates(BASE_PACKAGE);
    }

    @Test
    public void mapper() {
        //given
        final Movie movie = from(Movie.class).gimme(MOVIE);
        final MovieRequest movieRequest = from(MovieRequest.class).gimme(MOVIE_REQUEST);

        when(this.mapperFacade.map(movieRequest, Movie.class)).thenReturn(movie);

        // when
        final Movie result = mapper.map(movieRequest);

        //then
        assertEquals(movie, result);
    }

}
