package br.com.abbarros.moviechallenge.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;


@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = Movie.COLLECTION)
public class Movie implements Serializable {

    public static final String COLLECTION = "movie";

    @Id
    private String id;
    @Indexed
    private String name;
    private LocalDate launchDate;
    @Indexed
    private Censorship censorship;
    private String director;
    private List<String> cast;

}
