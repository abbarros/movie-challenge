package br.com.abbarros.moviechallenge.service.impl;

import br.com.abbarros.moviechallenge.exception.DuplicateMovieException;
import br.com.abbarros.moviechallenge.exception.MovieException;
import br.com.abbarros.moviechallenge.model.Censorship;
import br.com.abbarros.moviechallenge.model.Movie;
import br.com.abbarros.moviechallenge.repository.MovieRepository;
import br.com.abbarros.moviechallenge.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;

    @Override
    public Movie create(final Movie movie) {
        log.info("create");
        try {
            if(repository.existsByName(movie.getName()))
                throw new DuplicateMovieException(movie.getName());
            return repository.save(movie);
        } catch (final DuplicateMovieException e) {
            throw e;
        } catch (final Exception e) {
            throw new MovieException(String.format("Unable to create movie %s", movie.getName()));
        }
    }

    @Override
    public List<Movie> findAllByParameters(final Censorship censorship) {
        log.info("findAllByParameters");
        try {
            return repository.findAllByCensorship(censorship);
        } catch (final Exception e) {
            throw new MovieException(String.format("Unable to retrieve movie by censorship %s", censorship));
        }
    }

}
