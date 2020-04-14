package com.gra.worstmovieapp.controllers.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gra.worstmovieapp.entities.Movie;
import com.gra.worstmovieapp.entities.Producer;
import com.gra.worstmovieapp.entities.Studio;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static java.util.stream.Collectors.toList;

public class MovieBody {

    private final Long id;

    @NotNull
    private final Integer year;

    @NotNull
    private final String title;

    @NotEmpty
    private final List<String> studios;

    @NotEmpty
    private final List<String> producers;

    private final boolean winner;

    @JsonCreator
    public MovieBody(
            @JsonProperty("id") Long id,
            @JsonProperty("year") Integer year,
            @JsonProperty("title") String title,
            @JsonProperty("studios") List<String> studios,
            @JsonProperty("producers") List<String> producers,
            @JsonProperty("winner") boolean winner) {

        this.id = id;
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }

    public MovieBody(Movie movie) {
        this.id = movie.getId();
        this.year = movie.getYear();
        this.title = movie.getTitle();
        this.winner = movie.isWinner();

        this.studios = movie.getStudios().stream()
        .map(Studio::getName).collect(toList());

        this.producers = movie.getProducers().stream()
        .map(Producer::getName).collect(toList());
    }

    public Long getId() {
        return id;
    }

    public Integer getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getStudios() {
        return studios;
    }

    public List<String> getProducers() {
        return producers;
    }

    public boolean isWinner() {
        return winner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieBody that = (MovieBody) o;
        return winner == that.winner &&
                Objects.equals(id, that.id) &&
                Objects.equals(year, that.year) &&
                Objects.equals(title, that.title) &&
                Objects.equals(studios, that.studios) &&
                Objects.equals(producers, that.producers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, year, title, studios, producers, winner);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MovieBody.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("year=" + year)
                .add("title='" + title + "'")
                .add("studios=" + studios)
                .add("producers=" + producers)
                .add("winner=" + winner)
                .toString();
    }
}
