package com.gra.worstmovieapp.services;

import com.gra.worstmovieapp.controllers.entities.MovieBody;
import com.gra.worstmovieapp.entities.Movie;
import com.gra.worstmovieapp.entities.Producer;
import com.gra.worstmovieapp.entities.Studio;
import com.gra.worstmovieapp.repositories.MovieRepository;
import com.gra.worstmovieapp.repositories.ProducerRepository;
import com.gra.worstmovieapp.repositories.StudioRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final StudioRepository studioRepository;
    private final ProducerRepository producerRepository;

    public MovieService(MovieRepository movieRepository, StudioRepository studioRepository, ProducerRepository producerRepository) {
        this.movieRepository = movieRepository;
        this.studioRepository = studioRepository;
        this.producerRepository = producerRepository;
    }

    @Transactional
    public Optional<MovieBody> create(MovieBody movieRequest) {

        var studios = movieRequest.getStudios().stream()
        .map(studioName ->
            studioRepository.findByName(studioName.trim())
            .orElseGet(() -> studioRepository.save(new Studio(studioName)))
        ).collect(Collectors.toList());

        var producers = movieRequest.getProducers().stream()
        .map(producerName ->
            producerRepository.findByName(producerName.trim())
            .orElseGet(() -> producerRepository.save(new Producer(producerName)))
        ).collect(Collectors.toList());

        var movie = movieRepository.save(new Movie(
            movieRequest.getYear(),
            movieRequest.getTitle(),
            movieRequest.isWinner(),
            studios,
            producers
        ));

        return Optional.of(new MovieBody(movie));
    }

    public Optional<MovieBody> get(Long id) {
        return movieRepository.findById(id).map(MovieBody::new);
    }

    @Transactional
    public Optional<MovieBody> delete(Long id) {
        return movieRepository.findById(id)
        .map(movie -> {
            MovieBody movieBody = new MovieBody(movie);
            movieRepository.delete(movie);
            return movieBody;
        });
    }
}
