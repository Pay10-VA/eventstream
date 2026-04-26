package com.example.eventstream.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventstream.dto.HealthResponse;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/health")
public class Health {

  @GetMapping
  public HealthResponse health() {
    return new HealthResponse("OK", java.time.LocalDateTime.now().toString());
  }
}
