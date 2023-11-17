package com.example.securitytask.service;

import com.example.securitytask.model.CachedValue;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {
    private static final int MAX_ATTEMPT = 3;
    private static final int BLOCK_DURATION_SEC = 10;
    private final LoadingCache<String, CachedValue> attemptsCache;

    public LoginAttemptService() {
        attemptsCache = CacheBuilder.newBuilder()
                .expireAfterAccess(BLOCK_DURATION_SEC, TimeUnit.SECONDS)
                .build(new CacheLoader<>() {
                    @Override
                    public CachedValue load(String key) throws Exception {
                        return new CachedValue(0, LocalDateTime.now());
                    }
                });
    }

    public void loginFailed(final String key) {
        CachedValue cachedValue = new CachedValue();
        try {
            cachedValue = attemptsCache.get(key);
            cachedValue.setAttempts(cachedValue.getAttempts()+1);
        } catch (ExecutionException e) {
            cachedValue.setAttempts(0);
        }

        if(isBlocked(key) && cachedValue.getBlockedTimestamp() == null) {
            cachedValue.setBlockedTimestamp(LocalDateTime.now());
        }
        attemptsCache.put(key, cachedValue);
    }

    public boolean isBlocked(String key) {
        try {
            return attemptsCache.get(key).getAttempts() >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }
    }

}
