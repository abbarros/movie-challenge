package br.com.abbarros.moviechallenge.templates;

import br.com.abbarros.moviechallenge.model.Censorship;
import br.com.abbarros.moviechallenge.model.Movie;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDate;
import java.util.List;

import static br.com.six2six.fixturefactory.Fixture.of;

public class MovieTemplate implements TemplateLoader {

    @Override
    public void load() {
        of(Movie.class).addTemplate("movieValid", new Rule() {{
            add("name", "Coringa");
            add("launchDate", LocalDate.of(2020, 03, 31));
            add("censorship", Censorship.CENSURADO);
            add("director", "Todd Phillips");
            add("cast", List.of("Joaquin Phoenix", "Hannah Gross"));
        }});

        of(Movie.class).addTemplate("moviePersisted", new Rule() {{
            add("id", "5eed0ef8606b134046938a64");
            add("name", "Coringa");
            add("launchDate", LocalDate.of(2020, 03, 31));
            add("censorship", Censorship.CENSURADO);
            add("director", "Todd Phillips");
            add("cast", List.of("Joaquin Phoenix", "Hannah Gross"));
        }});
    }

}
