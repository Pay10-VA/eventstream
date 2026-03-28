package com.example.eventstream.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventstream.dto.EventRequest;



@RestController
@RequestMapping("/api/events")
public class EventController {

  @PostMapping
  public ResponseEntity<Void> postMethodName(@RequestBody EventRequest eventRequest) {
      return ResponseEntity.ok().build();
  }
  

  @GetMapping
  public String getMethodName() {
      return "hello world";
  }
  
  
}
