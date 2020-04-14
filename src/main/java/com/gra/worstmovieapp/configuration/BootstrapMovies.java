package com.gra.worstmovieapp.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.gra.worstmovieapp.controllers.entities.MovieBody;
import com.gra.worstmovieapp.services.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Configuration
public class BootstrapMovies {

    private static final Logger logger = LoggerFactory.getLogger(BootstrapMovies.class);

    @Bean
    public CommandLineRunner importMoviesCsv(MovieService movieService,
                                             @Value("classpath:/data/movie_list.csv") Resource movieListResource) {

        return args -> {

            var csvSchema = CsvSchema.emptySchema()
            .withColumnSeparator(';')
            .withHeader();

            new CsvMapper()
            .readerFor(MovieCsv.class).with(csvSchema)
            .readValues(movieListResource.getInputStream())
            .readAll().stream()
            .peek(csvMovieRow -> logger.info("CSV Movie Row {}", csvMovieRow))
            .map(csvMovieRow -> (MovieCsv) csvMovieRow)
            .map(MovieCsv::toMovieBody)
            .forEach(movieService::create);
        };
    }

    private static final class MovieCsv {

        private static final String SEPARATOR = ", | and ";

        private final Integer year;
        private final String title;
        private final String studios;
        private final String producers;
        private final String winner;

        public MovieCsv(
                @JsonProperty("year") Integer year,
                @JsonProperty("title") String title,
                @JsonProperty("studios") String studios,
                @JsonProperty("producers") String producers,
                @JsonProperty("winner") String winner) {
            this.year = year;
            this.title = title;
            this.studios = studios;
            this.producers = producers;
            this.winner = winner;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MovieCsv movieCsv = (MovieCsv) o;
            return Objects.equals(year, movieCsv.year) &&
                    Objects.equals(title, movieCsv.title) &&
                    Objects.equals(studios, movieCsv.studios) &&
                    Objects.equals(producers, movieCsv.producers) &&
                    Objects.equals(winner, movieCsv.winner);
        }

        @Override
        public int hashCode() {
            return Objects.hash(year, title, studios, producers, winner);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", MovieCsv.class.getSimpleName() + "[", "]")
                    .add("year=" + year)
                    .add("title='" + title + "'")
                    .add("studios='" + studios + "'")
                    .add("producers='" + producers + "'")
                    .add("winner='" + winner + "'")
                    .toString();
        }

        MovieBody toMovieBody() {

            var isWinner = this.winner.equals("yes");

            var studios = stream(this.studios.split(SEPARATOR))
            .map(String::trim)
            .collect(Collectors.toList());

            var producers = stream(this.producers.split(SEPARATOR))
            .map(String::trim)
            .collect(Collectors.toList());

            return new MovieBody(null, this.year, this.title, studios, producers, isWinner);
        }
    }
}
