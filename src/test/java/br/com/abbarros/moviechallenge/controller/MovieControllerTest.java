package br.com.abbarros.moviechallenge.controller;

import br.com.abbarros.moviechallenge.controller.mappers.MovieRequestMapper;
import br.com.abbarros.moviechallenge.controller.mappers.MovieResponseMapper;
import br.com.abbarros.moviechallenge.controller.request.MovieRequest;
import br.com.abbarros.moviechallenge.controller.response.MovieResponse;
import br.com.abbarros.moviechallenge.exception.MovieException;
import br.com.abbarros.moviechallenge.model.Censorship;
import br.com.abbarros.moviechallenge.model.Movie;
import br.com.abbarros.moviechallenge.service.MovieService;
import br.com.abbarros.moviechallenge.templates.Templates;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static br.com.six2six.fixturefactory.Fixture.from;
import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MovieControler.class)
@TestPropertySource(properties = {
        "spring.cloud.consul.config.enabled=false",
        "spring.cloud.vault.enabled=false"})
public class MovieControllerTest {

    private static final String MOVIE_VALID = "movieValid";
    private static final String MOVIE_PERSISTED = "moviePersisted";

    private static final String MOVIE_REQUEST_VALID = "movieRequestValid";
    private static final String MOVIE_REQUEST_EMPTY_NAME = "movieRequestEmptyName";
    private static final String MOVIE_REQUEST_NULL_NAME = "movieRequestNullName";
    private static final String MOVIE_REQUEST_NULL_LAUNCHDATE = "movieRequestNullLaunchDate";
    private static final String MOVIE_REQUEST_NULL_CENSORSHIP = "movieRequestNullCensorship";
    private static final String MOVIE_REQUEST_EMPTY_DIRECTOR = "movieRequestEmptyDirector";
    private static final String MOVIE_REQUEST_NULL_DIRECTOR = "movieRequestNullDirector";
    private static final String MOVIE_REQUEST_EMPTY_CAST = "movieRequestEmptyCast";
    private static final String MOVIE_REQUEST_NULL_CAST = "movieRequestNullCast";
    private static final String MOVIE_REQUEST_MINIMUM_AND_MAXIMUM_CAST = "movieRequestGreaterThanTheMaximumQuantityCast";
    private static final String MOVIE_RESPONSE_VALID = "movieResponseValid";

    private static final String ENDPOINT_MOVIE = "/movie";
    private static final String PARAM_CENSORSHIP = "censorship";

    private static final String MESSAGE_NAME_IS_REQUIRED = "Name is required";
    private static final String MESSAGE_LAUNCHDATE_IS_REQUIRED = "LaunchDate is required";
    private static final String MESSAGE_CENSORSHIP_IS_REQUIRED = "Censorship is required";
    private static final String MESSAGE_DIRECTOR_IS_REQUIRED = "Director is required";
    private static final String MESSAGE_CAST_IS_REQUIRED = "Cast is required";
    private static final String MESSAGE_CAST_MINIMUM_AND_MAXIMUM = "The cast must have a minimum of";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private MovieService service;

    @MockBean
    private MovieRequestMapper requestMapper;

    @MockBean
    private MovieResponseMapper responseMapper;

    @BeforeAll
    public static void beforeClass() {
        loadTemplates(Templates.BASE_PACKAGE);
    }

    @Test
    public void createSuccessfully() throws Exception {
        //given
        final Movie movie = from(Movie.class).gimme(MOVIE_VALID);
        final MovieResponse movieResponse = from(MovieResponse.class).gimme(MOVIE_RESPONSE_VALID);
        final Movie moviePersisted = from(Movie.class).gimme(MOVIE_PERSISTED);

        when(this.requestMapper.map(any(MovieRequest.class))).thenReturn(movie);
        when(this.responseMapper.map(any(Movie.class))).thenReturn(movieResponse);
        when(this.service.create(any(Movie.class))).thenReturn(moviePersisted);

        //when and then
        final MovieRequest movieRequest = from(MovieRequest.class).gimme(MOVIE_REQUEST_VALID);
        this.mockMvc.perform(
                post(ENDPOINT_MOVIE)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(moviePersisted.getId()));
    }

    @Test
    public void createMovieRequestEmptyNameThrowingAnException() throws Exception {
        //when
        final MovieRequest movieRequest = from(MovieRequest.class).gimme(MOVIE_REQUEST_EMPTY_NAME);
        final String response = this.mockMvc.perform(
                post(ENDPOINT_MOVIE)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();

        // then
        assertThat(response).contains(MESSAGE_NAME_IS_REQUIRED);
    }

    @Test
    public void createMovieRequestNullNameThrowingAnException() throws Exception {
        //when
        final MovieRequest movieRequest = from(MovieRequest.class).gimme(MOVIE_REQUEST_NULL_NAME);
        final String response = this.mockMvc.perform(
                post(ENDPOINT_MOVIE)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();

        // then
        assertThat(response).contains(MESSAGE_NAME_IS_REQUIRED);
    }

    @Test
    public void createMovieRequestNullLaunchDateThrowingAnException() throws Exception {
        //when
        final MovieRequest movieRequest = from(MovieRequest.class).gimme(MOVIE_REQUEST_NULL_LAUNCHDATE);
        final String response = this.mockMvc.perform(
                post(ENDPOINT_MOVIE)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();

        // then
        assertThat(response).contains(MESSAGE_LAUNCHDATE_IS_REQUIRED);
    }

    @Test
    public void createMovieRequestNullCensorshipThrowingAnException() throws Exception {
        //when
        final MovieRequest movieRequest = from(MovieRequest.class).gimme(MOVIE_REQUEST_NULL_CENSORSHIP);
        final String response = this.mockMvc.perform(
                post(ENDPOINT_MOVIE)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();

        // then
        assertThat(response).contains(MESSAGE_CENSORSHIP_IS_REQUIRED);
    }

    @Test
    public void createMovieRequestEmptyDirectorThrowingAnException() throws Exception {
        //when
        final MovieRequest movieRequest = from(MovieRequest.class).gimme(MOVIE_REQUEST_EMPTY_DIRECTOR);
        final String response = this.mockMvc.perform(
                post(ENDPOINT_MOVIE)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();

        // then
        assertThat(response).contains(MESSAGE_DIRECTOR_IS_REQUIRED);
    }

    @Test
    public void createMovieRequestNullDirectorThrowingAnException() throws Exception {
        //when
        final MovieRequest movieRequest = from(MovieRequest.class).gimme(MOVIE_REQUEST_NULL_DIRECTOR);
        final String response = this.mockMvc.perform(
                post(ENDPOINT_MOVIE)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();

        // then
        assertThat(response).contains(MESSAGE_DIRECTOR_IS_REQUIRED);
    }

    @Test
    public void createMovieRequestNullCastThrowingAnException() throws Exception {
        //when
        final MovieRequest movieRequest = from(MovieRequest.class).gimme(MOVIE_REQUEST_NULL_CAST);
        final String response = this.mockMvc.perform(
                post(ENDPOINT_MOVIE)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();

        // then
        assertThat(response).contains(MESSAGE_CAST_IS_REQUIRED);
    }

    @Test
    public void createMovieRequestEmptyCastThrowingAnException() throws Exception {
        //when
        final MovieRequest movieRequest = from(MovieRequest.class).gimme(MOVIE_REQUEST_EMPTY_CAST);
        final String response = this.mockMvc.perform(
                post(ENDPOINT_MOVIE)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();

        // then
        assertThat(response).contains(MESSAGE_CAST_MINIMUM_AND_MAXIMUM);
    }

    @Test
    public void createMovieRequestCastGreaterThanTheMaximumQuantityThrowingAnException() throws Exception {
        //when
        final MovieRequest movieRequest = from(MovieRequest.class).gimme(MOVIE_REQUEST_MINIMUM_AND_MAXIMUM_CAST);
        final String response = this.mockMvc.perform(
                post(ENDPOINT_MOVIE)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException()
                .getMessage();

        // then
        assertThat(response).contains(MESSAGE_CAST_MINIMUM_AND_MAXIMUM);
    }

    @Test
    public void createThrowingAnException() throws Exception {
        //given
        final Movie movie = from(Movie.class).gimme(MOVIE_VALID);
        final Throwable throwable = new MovieException("Unidentified error");

        when(this.requestMapper.map(any(MovieRequest.class))).thenReturn(movie);
        when(this.service.create(any(Movie.class))).thenThrow(throwable);

        //when and then
        final MovieRequest movieRequest = from(MovieRequest.class).gimme(MOVIE_REQUEST_VALID);
        this.mockMvc.perform(
                post(ENDPOINT_MOVIE)
                        .content(objectMapper.writeValueAsString(movieRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAllByParametersSuccessfully() throws Exception {
        //given
        final List<Movie> moviePersisted = from(Movie.class).gimme(2, MOVIE_PERSISTED);
        final MovieResponse movie = from(MovieResponse.class).gimme(MOVIE_RESPONSE_VALID);

        when(this.service.findAllByParameters(null)).thenReturn(moviePersisted);
        when(this.responseMapper.map(any(Movie.class))).thenReturn(movie);

        //when
        final String result = this.mockMvc.perform(
                get(ENDPOINT_MOVIE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        assertThat(objectMapper.writeValueAsString(moviePersisted))
                .isEqualToIgnoringWhitespace(result);
    }

    @Test
    public void findAllByParametersWithCensorshipValid() throws Exception {
        //given
        final List<Movie> moviePersisted = from(Movie.class).gimme(2, MOVIE_PERSISTED);
        final MovieResponse movie = from(MovieResponse.class).gimme(MOVIE_RESPONSE_VALID);

        when(this.service.findAllByParameters(any(Censorship.class))).thenReturn(moviePersisted);
        when(this.responseMapper.map(any(Movie.class))).thenReturn(movie);

        //when
        final String result = this.mockMvc.perform(
                get(ENDPOINT_MOVIE)
                        .param(PARAM_CENSORSHIP, Censorship.SEM_CENSURA.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        assertThat(objectMapper.writeValueAsString(moviePersisted))
                .isEqualToIgnoringWhitespace(result);
    }

    @Test
    public void findAllByParametersWithCensorshipInvalid() throws Exception {
        //when and then
        this.mockMvc.perform(
                get(ENDPOINT_MOVIE)
                        .param(PARAM_CENSORSHIP, "CENSORSHIP_INVALID")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findAllByParametersThrowingAnException() throws Exception {
        //given
        final Throwable throwable = new MovieException("Unidentified error");
        when(this.service.findAllByParameters(any(Censorship.class))).thenThrow(throwable);

        //when and then
        this.mockMvc.perform(
                get(ENDPOINT_MOVIE)
                        .param(PARAM_CENSORSHIP, Censorship.SEM_CENSURA.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
