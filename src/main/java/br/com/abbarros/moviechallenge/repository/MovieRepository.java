package br.com.abbarros.moviechallenge.repository;

import br.com.abbarros.moviechallenge.model.Censorship;
import br.com.abbarros.moviechallenge.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String>, MovieMongoOperation {

        List<Movie> findAllByCensorship(Censorship censorship);

}
