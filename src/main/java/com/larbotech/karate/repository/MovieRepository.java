package com.larbotech.karate.repository;

import com.larbotech.karate.model.Actor;
import com.larbotech.karate.model.Movie;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepository {

  private final HashMap<String, Movie> movies = new HashMap<>();

  public Optional<Movie> findById(String id) {
    return Optional.ofNullable(movies.get(id));
  }

  public Collection<Movie> findAll() {
    return movies.values();
  }

  public Movie add(Movie movie) {
    String id = UUID.randomUUID().toString();
    Movie persistedMovie = movie.copy(id);
    movies.put(id, persistedMovie);
    return persistedMovie;
  }

  public Optional<Movie> addActor(String idMovie, Actor actor) {
    if (movies.containsKey(idMovie)) {
      Movie movie = movies.get(idMovie);
      movie.getActors().add(actor);
      return Optional.of(movie);
    }
    return Optional.empty();
  }

  public List<Actor> findAllActors() {
    return movies.values().stream().flatMap(m -> m.getActors().stream())
        .collect(Collectors.toList());
  }
}
