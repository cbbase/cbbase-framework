package com.cbbase.core.cache;

import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;

import com.cbbase.core.common.ApplicationConfig;

/**
 * 
 * @author changbo
 *
 */
public class CacheManager {
	
	private static final String COMMON_CACHE = "commonCache";
	private static Cache<String, String> commonCache = null;
	
	static {
		initCache();
	}
	
	private synchronized static void initCache() {
		if(commonCache != null) {
			return;
		}
        PersistentCacheManager persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
        .with(CacheManagerBuilder.persistence(ApplicationConfig.getParam("system.cache", "/usr/local/webapp/cache")))
        .withCache(COMMON_CACHE,
            CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                    .heap(1000, EntryUnit.ENTRIES)  //堆
                    .offheap(100, MemoryUnit.MB)    //堆外
                    .disk(10, MemoryUnit.GB)      //磁盘
                )
        ).build(true);
        commonCache = persistentCacheManager.getCache(COMMON_CACHE, String.class, String.class);
	}
	
	public static void put(String key, String value) {
		commonCache.put(key, value);
	}
	
	public static String get(String key) {
		return commonCache.get(key);
	}

}
