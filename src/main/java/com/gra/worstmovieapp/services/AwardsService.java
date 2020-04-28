package com.gra.worstmovieapp.services;

import com.gra.worstmovieapp.controllers.entities.MinMaxWinningInterval;
import com.gra.worstmovieapp.entities.dto.ProducerWinningInterval;
import com.gra.worstmovieapp.repositories.ProducerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Service
public class AwardsService {

    private final ProducerRepository producerRepository;

    public AwardsService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    public Optional<MinMaxWinningInterval> getMinAndMaxWinningInterval() {

        return producerRepository.findProducersWithMultipleAwardWinningMovies()
        .map(producers -> {

            //Get all minimal interval from award winning producers...
            var producersWinningIntervals = producers.stream()
            .flatMap(ProducerWinningInterval::fromProducer)
            .sorted(comparing(ProducerWinningInterval::getInterval).reversed())
            .collect(toList());
            //... ordered by interval descending...

            if (producersWinningIntervals.isEmpty())
                return null;

            //...first from list has max interval and last has minimal interval
            final var maxInterval = producersWinningIntervals.get(0).getInterval();
            final var minInterval = producersWinningIntervals.get(producersWinningIntervals.size() - 1).getInterval();

            var maxIntervalList = new ArrayList<ProducerWinningInterval>();
            var minIntervalList = new ArrayList<ProducerWinningInterval>();

            producersWinningIntervals.forEach(producerWinningInterval -> {

                //Add to max list all with equal interval than max
                if (producerWinningInterval.getInterval().equals(maxInterval))
                    maxIntervalList.add(producerWinningInterval);

                //Add to min list all with equal interval than min
                if (producerWinningInterval.getInterval().equals(minInterval))
                    minIntervalList.add(producerWinningInterval);
            });

            return new MinMaxWinningInterval(minIntervalList, maxIntervalList);
        });
    }
}