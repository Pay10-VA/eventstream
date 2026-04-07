package com.example.eventstream.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventstream.model.EventCount;
import com.example.eventstream.model.EventCounts;
import com.example.eventstream.service.interfaces.AnalyticsService;




@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

  private final AnalyticsService analyticsService;

  public AnalyticsController(AnalyticsService analyticsService) {
    this.analyticsService = analyticsService; 
  }

  @GetMapping("/counts")
  public EventCounts getEventCounts() {
      return this.analyticsService.retrieveCurrEventCounts(null);
  }

  @GetMapping("/counts/user/{userId}")
  public EventCounts getCountByUserId(@PathVariable String userId) {
      return this.analyticsService.retrieveCurrEventCounts(userId);
  }

  @GetMapping("/top-events")
  public List<EventCount> getTop5Events() {
    return this.analyticsService.getTop5Events();
  }
  
  
}
