package com.cbbase.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cbbase.core.common.ApplicationConfig;
import com.cbbase.core.tools.IdWorker;

@Configuration
public class IdWorkerConfig {
	
    @Bean
    public IdWorker idWorker() {
    	long workerId = ApplicationConfig.getLong("idworker.workerId");
    	long dataCenterId = ApplicationConfig.getLong("idworker.dataCenterId");
        return new IdWorker(workerId, dataCenterId);
    }
    

}
