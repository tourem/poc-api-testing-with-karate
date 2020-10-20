package com.larbotech.karate.model;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.Data;

@Data
public class Movie {

  private String id = "";
  private String title;
  private List<Actor> actors = Lists.newArrayList();

  public Movie copy(String id){
    Movie movie = new Movie();
    movie.setActors(actors);
    movie.setTitle(title);
    movie.setId(id);
    return movie;
  }
}
