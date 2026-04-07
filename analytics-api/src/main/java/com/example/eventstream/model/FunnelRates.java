package com.example.eventstream.model;

import lombok.Data;

@Data
public class FunnelRates {

  @Data
  public static class FunnelStep {
    private double dropOffRate;
    private int count;
  }

  private int sessionStartCount;
  private FunnelStep pageView;
  private FunnelStep productView;
  private FunnelStep addToCart;
  private FunnelStep checkoutStart;
  private FunnelStep purchase;
  private double overallConversionRate;
}
