package br.com.abbarros.moviechallenge.repository;

public interface MovieMongoOperation {

    boolean existsByName(String name);

}
