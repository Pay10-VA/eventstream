package com.example.eventstream.service.interfaces;

import java.util.List;

import com.example.eventstream.model.EventCount;
import com.example.eventstream.model.EventCounts;
import com.example.eventstream.model.FunnelRates;
import com.example.eventstream.model.ProductViewCount;

public interface AnalyticsService {
  public EventCounts retrieveCurrEventCounts(String userId);
  public List<EventCount> getTop5Events();
  public List<ProductViewCount> getTop5ViewedProducts();
  public FunnelRates getFunnelAnalysis();
}
