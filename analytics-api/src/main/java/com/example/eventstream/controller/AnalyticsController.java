package com.example.eventstream.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventstream.model.EventCounts;
import com.example.eventstream.service.interfaces.EventService;


@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
  private final EventService eventService;

  public AnalyticsController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping("/counts")
  public EventCounts getEventCounts() {
      return this.eventService.retrieveCurrEventCounts();
  }
  
}
