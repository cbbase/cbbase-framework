package com.cbbase.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cbbase.core.tools.IdWorker;

@Configuration
public class IdWorkerConfig {
	
    @Value("${sequence.workerId}")
    private long workerId;
    
    @Value("${sequence.dataCenterId}")
    private long dataCenterId;
    
    @Bean
    public IdWorker idWorker() {
        return new IdWorker(workerId, dataCenterId);
    }
    

}
