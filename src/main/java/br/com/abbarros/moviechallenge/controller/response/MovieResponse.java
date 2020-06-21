package br.com.abbarros.moviechallenge.controller.response;

import br.com.abbarros.moviechallenge.model.Censorship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse implements Serializable {

    private String id;
    private String name;
    private LocalDate launchDate;
    private Censorship censorship;
    private String director;
    private List<String> cast;

}
