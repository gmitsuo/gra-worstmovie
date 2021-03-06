package com.gra.worstmovieapp.entities;

import com.gra.worstmovieapp.entities.dto.Interval;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

@Entity
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "producer_id_gen")
    private Long id;
    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "producers")
    private List<Movie> movies;

    public Producer() {}

    public Producer(String name) {
        this.name = name.trim();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public Stream<Interval> getWinningIntervals() {

        //Only producers with movies...
        if (this.movies == null)
            return Stream.empty();

        var sortedMovies = this.movies.stream()
        .filter(Movie::isWinner)
        .sorted(comparing(Movie::getYear))
        .collect(toList());

        //... or more than 2 award winning movies can have interval
        if (sortedMovies.size() < 2)
            return Stream.empty();

        //Calculate interval between each of the subsequent award winning movie...
        return range(0, sortedMovies.size() - 1)
        .mapToObj(index -> {
            Integer previousWin = sortedMovies.get(index).getYear();
            Integer followingWin = sortedMovies.get(index+1).getYear();
            return new Interval(previousWin, followingWin);
        });
        //... and return the min interval value among all
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return Objects.equals(id, producer.id) &&
                Objects.equals(name, producer.name) &&
                Objects.equals(movies, producer.movies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, movies);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Producer.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("movies=" + movies)
                .toString();
    }

}
