package com.example.eventstream.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventstream.dto.EventRequest;
import com.example.eventstream.service.interfaces.KafkaProducerService;




@RestController
@RequestMapping("/api/events")
public class EventController {

    private final KafkaProducerService kafkaProducer;

    public EventController(KafkaProducerService kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

  @PostMapping
  public ResponseEntity<Void> postMethodName(@RequestBody EventRequest eventRequest) {
      kafkaProducer.publishEvent(eventRequest);
      return ResponseEntity.ok().build();
  }
  
}
