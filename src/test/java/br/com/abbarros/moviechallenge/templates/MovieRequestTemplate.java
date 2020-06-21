package br.com.abbarros.moviechallenge.templates;

import br.com.abbarros.moviechallenge.controller.request.MovieRequest;
import br.com.abbarros.moviechallenge.model.Censorship;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDate;
import java.util.List;

import static br.com.six2six.fixturefactory.Fixture.of;

public class MovieRequestTemplate implements TemplateLoader {

    @Override
    public void load() {
        of(MovieRequest.class).addTemplate("movieRequestValid", new Rule() {{
            add("name", "Coringa");
            add("launchDate", LocalDate.of(2020, 03, 31));
            add("censorship", Censorship.CENSURADO);
            add("director", "Todd Phillips");
            add("cast", List.of("Joaquin Phoenix", "Hannah Gross"));
        }});
        of(MovieRequest.class).addTemplate("movieRequestEmptyName", new Rule() {{
            add("name", "");
            add("launchDate", LocalDate.of(2020, 03, 31));
            add("censorship", Censorship.CENSURADO);
            add("director", "Todd Phillips");
            add("cast", List.of("Joaquin Phoenix", "Hannah Gross"));
        }});
        of(MovieRequest.class).addTemplate("movieRequestNullName", new Rule() {{
            add("name", null);
            add("launchDate", LocalDate.of(2020, 03, 31));
            add("censorship", Censorship.CENSURADO);
            add("director", "Todd Phillips");
            add("cast", List.of("Joaquin Phoenix", "Hannah Gross"));
        }});
        of(MovieRequest.class).addTemplate("movieRequestNullLaunchDate", new Rule() {{
            add("name", "Coringa");
            add("launchDate", null);
            add("censorship", Censorship.CENSURADO);
            add("director", "Todd Phillips");
            add("cast", List.of("Joaquin Phoenix", "Hannah Gross"));
        }});
        of(MovieRequest.class).addTemplate("movieRequestNullCensorship", new Rule() {{
            add("name", "Coringa");
            add("launchDate", LocalDate.of(2020, 03, 31));
            add("censorship", null);
            add("director", "Todd Phillips");
            add("cast", List.of("Joaquin Phoenix", "Hannah Gross"));
        }});
        of(MovieRequest.class).addTemplate("movieRequestEmptyDirector", new Rule() {{
            add("name", "Coringa");
            add("launchDate", LocalDate.of(2020, 03, 31));
            add("censorship", Censorship.CENSURADO);
            add("director", "");
            add("cast", List.of("Joaquin Phoenix", "Hannah Gross"));
        }});
        of(MovieRequest.class).addTemplate("movieRequestNullDirector", new Rule() {{
            add("name", "Coringa");
            add("launchDate", LocalDate.of(2020, 03, 31));
            add("censorship", Censorship.CENSURADO);
            add("director", null);
            add("cast", List.of("Joaquin Phoenix", "Hannah Gross"));
        }});
        of(MovieRequest.class).addTemplate("movieRequestEmptyCast", new Rule() {{
            add("name", "Coringa");
            add("launchDate", LocalDate.of(2020, 03, 31));
            add("censorship", Censorship.CENSURADO);
            add("director", "Todd Phillips");
            add("cast", List.of());
        }});
        of(MovieRequest.class).addTemplate("movieRequestNullCast", new Rule() {{
            add("name", "Coringa");
            add("launchDate", LocalDate.of(2020, 03, 31));
            add("censorship", Censorship.CENSURADO);
            add("director", "Todd Phillips");
            add("cast", null);
        }});
        of(MovieRequest.class).addTemplate("movieRequestGreaterThanTheMaximumQuantityCast", new Rule() {{
            add("name", "Coringa");
            add("launchDate", LocalDate.of(2020, 03, 31));
            add("censorship", Censorship.CENSURADO);
            add("director", "Todd Phillips");
            add("cast", List.of("Joaquin Phoenix", "Hannah Gross", "Joaquin Phoenix", "Hannah Gross", "Joaquin Phoenix",
                    "Hannah Gross", "Joaquin Phoenix", "Hannah Gross", "Joaquin Phoenix", "Hannah Gross", "Joaquin Phoenix", "Hannah Gross"));
        }});
    }

}
