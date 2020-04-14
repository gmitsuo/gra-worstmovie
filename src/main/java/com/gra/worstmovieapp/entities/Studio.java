package com.gra.worstmovieapp.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "studio_id_gen")
    private Long id;
    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "studios")
    private List<Movie> movies;

    public Studio(){}

    public Studio(String name) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Studio studio = (Studio) o;
        return Objects.equals(id, studio.id) &&
                Objects.equals(name, studio.name) &&
                Objects.equals(movies, studio.movies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, movies);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Studio.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("movies=" + movies)
                .toString();
    }
}
