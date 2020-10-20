package com.larbotech.karate.controller;

import com.larbotech.karate.model.Actor;
import com.larbotech.karate.repository.MovieRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actors")
public class ActorController {

  @Autowired
  private MovieRepository movieRepository;


  @GetMapping
  public List<Actor> allActors() {
    return movieRepository.findAllActors();
  }

}
