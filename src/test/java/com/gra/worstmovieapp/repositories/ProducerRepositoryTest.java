package com.gra.worstmovieapp.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class ProducerRepositoryTest {

    @Autowired
    ProducerRepository producerRepository;

    @Test
    @Sql(scripts = {"classpath:scripts/clear-tables.sql", "classpath:scripts/producer-repository/no-movie-winning-producer.sql"})
    void findProducersWithMultipleAwardWinningMoviesWithNoWinningProducers() {
        assertThat(producerRepository.findProducersWithMultipleAwardWinningMovies())
        .isEmpty();
    }

    @Test
    @Sql(scripts = {"classpath:scripts/clear-tables.sql", "classpath:scripts/producer-repository/with-single-and-multiple-winning-movie-producers.sql"})
    void findProducersWithMultipleAwardWinningMoviesWithSingleAndMultipleWinners() {
        assertThat(producerRepository.findProducersWithMultipleAwardWinningMovies())
        .isNotEmpty().get()
        .matches(producers ->
            producers.size() == 1 &&
            producers.get(0).getId().equals(2L) &&
            producers.get(0).getName().equals("PRODUCER2")
        );
    }
}