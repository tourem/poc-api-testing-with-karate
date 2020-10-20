package com.larbotech.karate.controller;

import com.larbotech.karate.model.Actor;
import com.larbotech.karate.model.Movie;
import com.larbotech.karate.repository.MovieRepository;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequestMapping("/movies")
public class MovieController {

  @Autowired
  private MovieRepository movieRepository;

  @PostMapping
  public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
    Movie persistedMovie = movieRepository.add(movie);
    String id = persistedMovie.getId();
    return ResponseEntity.created(MvcUriComponentsBuilder
        .fromMethodName(MovieController.class, "getMovie", id)
        .build()
        .toUri()
    ).body(persistedMovie);
  }

  @GetMapping
  public Collection<Movie> allMovies() {
    return movieRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Movie> getMovie(@PathVariable("id") String id) {
    return movieRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().
        build());
  }

  @PostMapping("/{id}/actors")
  public ResponseEntity<Movie> addActor(@PathVariable("id") String id, @RequestBody Actor actor) {
    return movieRepository.addActor(id, actor).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().
            build());
  }


}
