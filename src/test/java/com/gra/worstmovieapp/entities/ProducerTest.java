package com.gra.worstmovieapp.entities;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProducerTest {

    @Test
    void noMovieShouldReturnNoInterval() {
        assertThat(new Producer().getMinWinningInterval())
        .isEmpty();
    }

    @Test
    void oneMovieShouldReturnNoInterval() {

        var producer = new Producer();
        producer.setMovies(List.of(new Movie()));

        assertThat(producer.getMinWinningInterval())
        .isEmpty();
    }

    @Test
    void shouldReturnWithMinimalInterval() {

        var producer = new Producer();

        List<Producer> producers = List.of(producer);
        List<Studio> studios = Collections.emptyList();

        var movie5 = new Movie(1914, "5", true, studios, producers);
        var movie4 = new Movie(1912, "4", true, studios, producers);
        var movie3 = new Movie(1909, "3", true, studios, producers);
        var movie2 = new Movie(1905, "2", true, studios, producers);
        var movie1 = new Movie(1900, "1", true, studios, producers);

        producer.setMovies(List.of(movie5, movie4, movie3, movie2, movie1));

        assertThat(producer.getMinWinningInterval())
        .isNotEmpty().get()
        .matches(interval ->
            interval.getDiff().equals(2) &&
            interval.getPrevious().equals(1912) &&
            interval.getFollowing().equals(1914)
        );
    }

    @Test
    void shouldUseOnlyWinningMovies() {

        var producer = new Producer();

        List<Producer> producers = List.of(producer);
        List<Studio> studios = Collections.emptyList();

        var movie4 = new Movie(1906, "4", false, studios, producers);
        var movie3 = new Movie(1905, "3", true, studios, producers);
        var movie2 = new Movie(1903, "2", true, studios, producers);
        var movie1 = new Movie(1900, "1", true, studios, producers);

        producer.setMovies(List.of(movie4, movie3, movie2, movie1));

        assertThat(producer.getMinWinningInterval())
        .isNotEmpty().get()
        .matches(interval ->
            interval.getDiff().equals(2) &&
            interval.getPrevious().equals(1903) &&
            interval.getFollowing().equals(1905)
        );
    }
}