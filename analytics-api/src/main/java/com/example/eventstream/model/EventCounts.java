package com.example.eventstream.model;

import lombok.Data;

@Data
public class EventCounts {
  private int pageViewCount;
  private int productViewCount;
  private int addToCartCount;
  private int removeFromCartCount;
  private int checkoutStartCount;
  private int purchaseCount;
  private int searchCount;
  private int sessionStartCount;
}
