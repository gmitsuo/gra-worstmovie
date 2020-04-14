package com.gra.worstmovieapp.controllers;

import com.gra.worstmovieapp.controllers.entities.MovieBody;
import com.gra.worstmovieapp.services.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody MovieBody movieBody, UriComponentsBuilder uriBuilder) {

        return movieService.create(movieBody)
        .map(movie -> {
            UriComponents locationUri = uriBuilder.path("/movie/{id}").buildAndExpand(movie.getId());
            return ResponseEntity.created(locationUri.toUri()).body(movie);
        })
        .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        return ResponseEntity.of(movieService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return movieService.delete(id)
        .map(ResponseEntity::ok)
        .orElseGet(ResponseEntity.noContent()::build);
    }
}