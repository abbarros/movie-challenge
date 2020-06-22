package br.com.abbarros.moviechallenge.repository.impl;

import br.com.abbarros.moviechallenge.model.Movie;
import br.com.abbarros.moviechallenge.repository.MovieMongoOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MovieMongoOperationImpl implements MovieMongoOperation {

    private final MongoOperations mongoOperations;

    @Override
    public boolean existsByName(final String name) {
        Criteria regex = Criteria.where("name").regex("^" + name + "$", "i");
        return mongoOperations.find(new Query().addCriteria(regex), Movie.class).size() > 0;
    }

}
