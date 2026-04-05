package com.example.eventstream.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventstream.dto.EventRequest;
import com.example.eventstream.service.interfaces.KafkaProducerService;


import com.example.eventstream.model.EventCounts;
import com.example.eventstream.service.interfaces.EventService;




@RestController
@RequestMapping("/api/events")
public class EventController {

    private KafkaProducerService kafkaProducer;
    private final EventService eventService;

    public EventController(KafkaProducerService kafkaProducer, EventService eventService) {
        this.kafkaProducer = kafkaProducer;
        this.eventService = eventService;
    }

  @PostMapping
  public ResponseEntity<Void> postMethodName(@RequestBody EventRequest eventRequest) {
      kafkaProducer.publishEvent(eventRequest);
      return ResponseEntity.ok().build();
  }

  @GetMapping
  public EventCounts getEventCounts() {
      return this.eventService.retrieveCurrEventCounts();
  }
  
  

  @GetMapping("/")
  public String getMethodName() {
      return "hello world";
  }
  
  
}
