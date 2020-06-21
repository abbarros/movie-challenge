package br.com.abbarros.moviechallenge.controller;

import br.com.abbarros.moviechallenge.controller.mappers.MovieRequestMapper;
import br.com.abbarros.moviechallenge.controller.mappers.MovieResponseMapper;
import br.com.abbarros.moviechallenge.controller.request.MovieRequest;
import br.com.abbarros.moviechallenge.controller.response.MovieResponse;
import br.com.abbarros.moviechallenge.model.Censorship;
import br.com.abbarros.moviechallenge.model.Movie;
import br.com.abbarros.moviechallenge.service.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Api(tags = "Register Movies", protocols = "http")
@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieControler {

    private final MovieService service;
    private final MovieRequestMapper requestMapper;
    private final MovieResponseMapper responseMapper;

    @ApiOperation(value = "Find All Movies by Parameters")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    MovieResponse create(@RequestBody @Valid MovieRequest movieRequest,
                         @RequestHeader(value = "correlationId", required = false) String correlationId) {
        log.info("Starting movie creation");
        try {
            final Movie moviePersisted = service.create(requestMapper.map(movieRequest));
            final MovieResponse movie = responseMapper.map(moviePersisted);
            log.info("Finishing movie creation id: {}", movie.getId());
            return movie;
        } catch(final Exception e) {
            log.error("Error create: {}", e.getMessage());
            throw e;
        }
    }

    @ApiOperation(value = "Create a Movie")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MovieResponse> findAllByParameters(@RequestParam(value = "censorship", required = false) Censorship censorship,
                                                   @RequestHeader(value = "correlationId", required = false) String correlationId) {
        log.info("Starting find all movies by parameters {}", censorship);
        try {
            final List<MovieResponse> movies = this.service.findAllByParameters(censorship)
                    .stream()
                    .map(responseMapper::map)
                    .collect(Collectors.toList());
            log.info("Finishing find all movies");
            return movies;
        } catch(final Exception e) {
            log.error("Error finishing find all movies: {}", e.getMessage());
            throw e;
        }
    }

}