package br.com.abbarros.moviechallenge.controller.request;

import br.com.abbarros.moviechallenge.model.Censorship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest implements Serializable {

    @NotEmpty(message = "Name is required")
    private String name;
    @NotNull(message = "LaunchDate is required")
    private LocalDate launchDate;
    @NotNull(message = "Censorship is required")
    private Censorship censorship;
    @NotEmpty(message = "Director is required")
    private String director;
    @NotNull(message = "Cast is required")
    @Size(min = 1, max = 10, message = "The cast must have a minimum of {min} and a maximum of {max} actors.")
    private List<String> cast;

}
