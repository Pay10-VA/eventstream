package com.example.eventstream.service.interfaces;

import java.util.List;

import com.example.eventstream.model.EventCount;
import com.example.eventstream.model.EventCounts;

public interface AnalyticsService {
  public EventCounts retrieveCurrEventCounts(String userId);
  public List<EventCount> getTop5Events();
}
