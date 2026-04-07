package com.example.eventstream.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.eventstream.enums.EventType;
import com.example.eventstream.model.EventCount;
import com.example.eventstream.model.EventCounts;
import com.example.eventstream.model.FunnelRates;
import com.example.eventstream.model.ProductViewCount;
import com.example.eventstream.repository.interfaces.EventRepositoryInterface;
import com.example.eventstream.repository.interfaces.RedisRepositoryInterface;
import com.example.eventstream.service.interfaces.AnalyticsService;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
  private final RedisRepositoryInterface redisRepository;
  private final EventRepositoryInterface eventRepository;

  public AnalyticsServiceImpl(RedisRepositoryInterface redisRepository, EventRepositoryInterface eventRepository) {
    this.redisRepository = redisRepository;
    this.eventRepository = eventRepository;
  }

  @Override
  public EventCounts retrieveCurrEventCounts(String userId) {
    EventCounts eventCounts = new EventCounts();

    // Iterate through all event types and get the count for each one
    for (EventType eventType : EventType.values()) {
      String key = (userId != null) ? userId + ":" + eventType.name() : eventType.name();
      int count = redisRepository.getCountLastTenMinutes(key);
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

  @Override
  public List<EventCount> getTop5Events() {
    return this.eventRepository.getTop5MostFrequentEvents();
  }

  @Override
  public List<ProductViewCount> getTop5ViewedProducts() {
    return this.eventRepository.getTop5ViewedProducts();
  }

  private double calculateDropOffRate(int previousStep, int currentStep) {
    return previousStep > 0 ? (previousStep - currentStep) * 100.0 / previousStep : 0.0;
  }

  @Override
  public FunnelRates getFunnelAnalysis() {
    List<EventCount> eventCounts = this.eventRepository.getEventCountsByEventType();

    // Build Map once
    Map<String, Integer> eventCountMap = eventCounts.stream()
        .collect(Collectors.toMap(EventCount::getEventType, EventCount::getCount)); 

    FunnelRates funnelRates = new FunnelRates();

    // Session Start
    int sessionStart = eventCountMap.getOrDefault(EventType.SESSION_START.name(), 0);
    funnelRates.setSessionStartCount(sessionStart);

    // Page View
    FunnelRates.FunnelStep pageViewStep = new FunnelRates.FunnelStep();
    pageViewStep.setCount(eventCountMap.getOrDefault(EventType.PAGE_VIEW.name(), 0));
    double pageViewDropOffRate = calculateDropOffRate(sessionStart, pageViewStep.getCount());
    pageViewStep.setDropOffRate(pageViewDropOffRate);
    funnelRates.setPageView(pageViewStep);

    // Product View
    FunnelRates.FunnelStep productViewStep = new FunnelRates.FunnelStep();
    productViewStep.setCount(eventCountMap.getOrDefault(EventType.PRODUCT_VIEW.name(), 0));
    double productViewDropOffRate = calculateDropOffRate(pageViewStep.getCount(), productViewStep.getCount());
    productViewStep.setDropOffRate(productViewDropOffRate);
    funnelRates.setProductView(productViewStep);

    // Add to Cart
    FunnelRates.FunnelStep addToCartStep = new FunnelRates.FunnelStep();
    addToCartStep.setCount(eventCountMap.getOrDefault(EventType.ADD_TO_CART.name(), 0));
    double addToCartDropOffRate = calculateDropOffRate(productViewStep.getCount(), addToCartStep.getCount());
    addToCartStep.setDropOffRate(addToCartDropOffRate);
    funnelRates.setAddToCart(addToCartStep);  

    // Checkout Start
    FunnelRates.FunnelStep checkoutStartStep = new FunnelRates.FunnelStep();
    checkoutStartStep.setCount(eventCountMap.getOrDefault(EventType.CHECKOUT_START.name(), 0));
    double checkoutStartDropOffRate = calculateDropOffRate(addToCartStep.getCount(), checkoutStartStep.getCount());
    checkoutStartStep.setDropOffRate(checkoutStartDropOffRate);
    funnelRates.setCheckoutStart(checkoutStartStep);

    // Purchase
    FunnelRates.FunnelStep purchaseStep = new FunnelRates.FunnelStep();
    purchaseStep.setCount(eventCountMap.getOrDefault(EventType.PURCHASE.name(), 0));
    double purchaseDropOffRate = calculateDropOffRate(checkoutStartStep.getCount(), purchaseStep.getCount());
    purchaseStep.setDropOffRate(purchaseDropOffRate);
    funnelRates.setPurchase(purchaseStep);

    // overallConversionRate
    double overallConversionRate = sessionStart > 0 ? purchaseStep.getCount() * 100.0 / sessionStart : 0.0;
    funnelRates.setOverallConversionRate(overallConversionRate);

    return funnelRates;
  }
}
