package com.example.eventstream.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.eventstream.repository.interfaces.RedisRepositoryInterface;


@Repository
public class RedisRepositoryImpl implements RedisRepositoryInterface {
  private final int LAST_10_MINUTES_MS = 10 * 60 * 1000;

  private final RedisTemplate<String, String> redisTemplate;

  public RedisRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public void recordEvent(String key) {
    long now = System.currentTimeMillis();
    redisTemplate.opsForZSet().add(key, String.valueOf(now), now);
  }

  @Override
  public int getCountLastTenMinutes(String key) {
    long now = System.currentTimeMillis();
    long windowStart = now - this.LAST_10_MINUTES_MS;

    // Remove everything from the beginning of time(0) to 10 minutes ago (windowStart)
    redisTemplate.opsForZSet().removeRangeByScore(key, 0, windowStart);

    // Count what's left over
    Long count = redisTemplate.opsForZSet().size(key);
    return count != null ? count.intValue() : 0;
  }
}