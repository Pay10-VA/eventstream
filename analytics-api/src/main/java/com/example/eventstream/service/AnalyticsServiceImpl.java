package com.example.eventstream.service;

import org.springframework.stereotype.Service;

import com.example.eventstream.enums.EventType;
import com.example.eventstream.model.EventCounts;
import com.example.eventstream.repository.interfaces.RedisRepositoryInterface;
import com.example.eventstream.service.interfaces.AnalyticsService;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
  private final RedisRepositoryInterface redisRepository;

  public AnalyticsServiceImpl(RedisRepositoryInterface redisRepository) {
    this.redisRepository = redisRepository;
  }

  @Override
  public EventCounts retrieveCurrEventCounts() {
    EventCounts eventCounts = new EventCounts();

    // Iterate through all event types and get the count for each one
    for (EventType eventType : EventType.values()) {
      int count = redisRepository.getCountLastTenMinutes(eventType.name()); 
      switch (eventType) {
        case PAGE_VIEW:
          eventCounts.setPageViewCount(count);
          break;
        case PRODUCT_VIEW:
          eventCounts.setProductViewCount(count);
          break;
        case ADD_TO_CART:
          eventCounts.setAddToCartCount(count);
          break;
        case REMOVE_FROM_CART:
          eventCounts.setRemoveFromCartCount(count);
          break;
        case CHECKOUT_START:
          eventCounts.setCheckoutStartCount(count);
          break;
        case PURCHASE:
          eventCounts.setPurchaseCount(count);
          break;
        case SEARCH:
          eventCounts.setSearchCount(count);
          break;
        case SESSION_START:
          eventCounts.setSessionStartCount(count);
          break;
      }
    }
    return eventCounts;
  }
}
