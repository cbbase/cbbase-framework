package com.cbbase.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cbbase.core.common.ApplicationConfig;
import com.cbbase.core.tools.IdWorker;

@Configuration
public class IdWorkerConfig {
	
    @Bean
    public IdWorker idWorker() {
    	long workerId = ApplicationConfig.getLong("id.workerId");
    	long dataCenterId = ApplicationConfig.getLong("id.dataCenterId");
    	workerId = workerId == 0 ? 1 : workerId;
    	dataCenterId = dataCenterId == 0 ? 1 : dataCenterId;
        return new IdWorker(workerId, dataCenterId);
    }
    

}
