package com.gra.worstmovieapp.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "movie_id_gen")
    private Long id;
    private Integer year;
    private String title;
    private boolean winner;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Studio> studios;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Producer> producers;

    public Movie() {}

    public Movie(Integer year, String title, boolean winner, List<Studio> studios, List<Producer> producers) {
        this.year = year;
        this.title = title;
        this.winner = winner;
        this.studios = studios;
        this.producers = producers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public List<Studio> getStudios() {
        return studios;
    }

    public void setStudios(List<Studio> studios) {
        this.studios = studios;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return winner == movie.winner &&
                Objects.equals(id, movie.id) &&
                Objects.equals(year, movie.year) &&
                Objects.equals(title, movie.title) &&
                Objects.equals(studios, movie.studios) &&
                Objects.equals(producers, movie.producers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, year, title, winner, studios, producers);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Movie.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("year=" + year)
                .add("title='" + title + "'")
                .add("winner=" + winner)
                .add("studios=" + studios)
                .add("producers=" + producers)
                .toString();
    }
}
