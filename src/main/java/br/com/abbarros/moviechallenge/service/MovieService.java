package br.com.abbarros.moviechallenge.service;

import br.com.abbarros.moviechallenge.model.Censorship;
import br.com.abbarros.moviechallenge.model.Movie;

import java.util.List;

public interface MovieService {

    Movie create(final Movie movie);

    List<Movie> findAllByParameters(final Censorship censorship);

}
