package com.example.eventstream.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventstream.dto.EventRequest;
import com.example.eventstream.service.interfaces.EventService;
import com.example.eventstream.service.interfaces.KafkaProducerService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.eventstream.model.EventRecord;





@RestController
@RequestMapping("/api/events")
public class EventController {

    private final KafkaProducerService kafkaProducer;
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

  @GetMapping("/user/{userId}")
  public Page<EventRecord> getEventsByUserId(@PathVariable String userId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
      return this.eventService.getEventsByUserId(userId, page, size);
  }

  @GetMapping("/session/{sessionId}")
  public Page<EventRecord> getEventsBySessionId(@PathVariable String sessionId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
      return this.eventService.getEventsBySessionId(sessionId, page, size);
  }
  
}
