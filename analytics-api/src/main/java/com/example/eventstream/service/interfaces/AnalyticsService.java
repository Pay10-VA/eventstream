package com.example.eventstream.service.interfaces;

import com.example.eventstream.model.EventCounts;

public interface AnalyticsService {
  public EventCounts retrieveCurrEventCounts(String userId);
}
