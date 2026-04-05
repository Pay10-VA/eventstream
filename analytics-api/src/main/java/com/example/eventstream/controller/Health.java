package com.example.eventstream.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/health")
public class Health {

  @GetMapping
  public String health() {
    return "OK";
  }  
}
