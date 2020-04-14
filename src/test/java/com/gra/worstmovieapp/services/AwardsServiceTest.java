package com.gra.worstmovieapp.services;

import com.gra.worstmovieapp.entities.Movie;
import com.gra.worstmovieapp.entities.Producer;
import com.gra.worstmovieapp.entities.Studio;
import com.gra.worstmovieapp.entities.dto.ProducerWinningInterval;
import com.gra.worstmovieapp.repositories.ProducerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AwardsServiceTest {

    @MockBean
    ProducerRepository producerRepository;

    AwardsService awardsService;

    @BeforeEach
    void setup() {
        this.awardsService = new AwardsService(producerRepository);
    }

    @Test
    void noMultipleAwardWinningProducerShouldReturnEmpty() {

        when(producerRepository.findProducersWithMultipleAwardWinningMovies())
        .thenReturn(Optional.empty());

        assertThat(awardsService.getMinAndMaxWinningInterval())
        .isEmpty();
    }

    @Test
    void getMinAndMaxWinningInterval() {

        List<Studio> studios = emptyList();
        List<Producer> producers = emptyList();

        var movie2 = new Movie(1902, "2", true, studios, producers);
        var movie1 = new Movie(1900, "1", true, studios, producers);

        String producerMin = "ProducerMin";
        var producerMinInterval2 = new Producer(producerMin);
        producerMinInterval2.setMovies(List.of(movie2, movie1));

        var movie4 = new Movie(1903, "4", true, studios, producers);
        var movie3 = new Movie(1900, "3", true, studios, producers);

        var producerMinInterval3 = new Producer("ProducerMiddle");
        producerMinInterval3.setMovies(List.of(movie3, movie4));

        var movie6 = new Movie(1904, "6", true, studios, producers);
        var movie5 = new Movie(1900, "5", true, studios, producers);

        String producerMax = "ProducerMax";
        var producerMinInterval4 = new Producer(producerMax);
        producerMinInterval4.setMovies(List.of(movie5, movie6));


        when(producerRepository.findProducersWithMultipleAwardWinningMovies())
        .thenReturn(Optional.of(List.of(producerMinInterval2, producerMinInterval3, producerMinInterval4)));

        assertThat(awardsService.getMinAndMaxWinningInterval())
        .isNotEmpty().get().matches(minMaxInterval ->
            minMaxInterval.getMin().size() == 1 &&
            minMaxInterval.getMax().size() == 1 &&
            minMaxInterval.getMin().contains(new ProducerWinningInterval(producerMin, 2, 1900, 1902)) &&
            minMaxInterval.getMax().contains(new ProducerWinningInterval(producerMax, 4, 1900, 1904))
        );
    }

}