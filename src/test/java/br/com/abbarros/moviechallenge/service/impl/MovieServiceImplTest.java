package br.com.abbarros.moviechallenge.service.impl;

import br.com.abbarros.moviechallenge.exception.DuplicateMovieException;
import br.com.abbarros.moviechallenge.exception.MovieException;
import br.com.abbarros.moviechallenge.model.Censorship;
import br.com.abbarros.moviechallenge.model.Movie;
import br.com.abbarros.moviechallenge.repository.MovieRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static br.com.abbarros.moviechallenge.templates.Templates.BASE_PACKAGE;
import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class MovieServiceImplTest {

    private static final String MOVIE_VALID = "movieValid";
    private static final String MOVIE_PERSISTED = "moviePersisted";

    @InjectMocks
    private MovieServiceImpl service;

    @Mock
    private MovieRepository repository;

    @BeforeAll
    public static void beforeClass() {
        loadTemplates(BASE_PACKAGE);
    }

    @Test
    public void createMovieSuccessfully() {
        //given
        final Movie movie = from(Movie.class).gimme(MOVIE_VALID);
        final Movie moviePersisted = from(Movie.class).gimme(MOVIE_PERSISTED);

        when(this.repository.save(movie)).thenReturn(moviePersisted);
        when(this.repository.existsByName(anyString())).thenReturn(FALSE);

        //when
        final Movie result = service.create(movie);

        //then
        assertNotNull(result);
        assertThat(result, samePropertyValuesAs(movie, "id"));
    }

    @Test
    public void createMovieThrowingAnDuplicateMovieException() {
        //given
        final Movie movie = from(Movie.class).gimme(MOVIE_VALID);
        final String message = String.format("It was not possible to register because there is a movie with the name %s registered", movie.getName());

        when(this.repository.existsByName(anyString())).thenReturn(TRUE);

        //when and then
        final DuplicateMovieException thrown = assertThrows(DuplicateMovieException.class, () -> {
            service.create(movie);
        });
        assertEquals(thrown.getMessage(), message);
    }

    @Test
    public void createMovieThrowingAnException() {
        //given
        final Movie movie = from(Movie.class).gimme(MOVIE_VALID);
        final String message = String.format("Unable to create movie %s", movie.getName());
        final Throwable throwable = new MovieException(message);

        when(this.repository.existsByName(anyString())).thenReturn(FALSE);
        when(this.repository.save(any(Movie.class))).thenThrow(throwable);

        //when and then
        final MovieException thrown = assertThrows(MovieException.class, () -> {
            service.create(movie);
        });
        assertEquals(thrown.getMessage(), message);
    }

    @Test
    public void findAllByParametersReturnResult() {
        //given
        final Censorship censorship = Censorship.CENSURADO;
        final List<Movie> movies = from(Movie.class).gimme(2, MOVIE_VALID);

        when(this.repository.findAllByCensorship(any(Censorship.class))).thenReturn(movies);

        //when
        final List<Movie> result = service.findAllByParameters(censorship);

        //then
        assertFalse(result.isEmpty());
        assertEquals(result, movies, "Arrays should be equal");
    }

    @Test
    public void findAllByParametersNoReturnResult() {
        //given
        final Censorship censorship = Censorship.CENSURADO;
        final List<Movie> movies = List.of();

        when(this.repository.findAllByCensorship(any(Censorship.class))).thenReturn(movies);

        //when
        final List<Movie> result = service.findAllByParameters(censorship);

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    public void findAllByParametersThrowingAnException() {
        //given
        final Censorship censorship = Censorship.CENSURADO;
        final String message = String.format("Unable to retrieve movie by censorship %s", censorship);
        final Throwable throwable = new MovieException(message);

        when(this.repository.findAllByCensorship(any(Censorship.class))).thenThrow(throwable);

        //when and then
        final MovieException thrown = assertThrows(MovieException.class, () -> {
            service.findAllByParameters(censorship);
        });
         assertEquals(thrown.getMessage(), message);
    }

}
