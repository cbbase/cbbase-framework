package com.cbbase.core.config;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * 
 * 	本地缓存,基于Caffeine
 * @author changbo
 *
 */
@Configuration
@EnableCaching //开启缓存
public class LocalCacheConfig {
	
	public static final String DEFAULT_NAME = "default";
    public static final int DEFAULT_MAXSIZE = 50000;
    public static final int DEFAULT_TTL = 60;//秒
    
    /**
     * 创建的CacheManager
     * @return
     */
    @Bean
    @Primary
    public CacheManager caffeineCacheManager() {
    	CaffeineCache cache = new CaffeineCache(DEFAULT_NAME,
                Caffeine.newBuilder().recordStats()
                        .expireAfterWrite(DEFAULT_TTL, TimeUnit.SECONDS)
                        .maximumSize(DEFAULT_MAXSIZE)
                        .build());
    	
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        ArrayList<CaffeineCache> caches = new ArrayList<>();
        caches.add(cache);
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
