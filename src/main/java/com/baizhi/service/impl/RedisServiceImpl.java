package com.baizhi.service.impl;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl {
    public String get(String redisKey) {

        return redisKey;
    }

    public String set(String redisKey, String yes, long time) {
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;

        return redisKey;
    }
}
