package com.example.eventstream.repository.interfaces;

public interface RedisRepositoryInterface {
  public void recordEvent(String key);

  public int getCountLastTenMinutes(String key);
}
