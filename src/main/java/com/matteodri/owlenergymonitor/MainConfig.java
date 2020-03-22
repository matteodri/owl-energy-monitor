package com.matteodri.owlenergymonitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.matteodri.owlenergymonitor.services.OwlMessageProcessor;
import com.matteodri.owlenergymonitor.services.OwlMessageProcessorImpl;
import com.matteodri.owlenergymonitor.util.MeasurementsUnmarshaller;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Spring bean configuration.
 */
@Configuration
public class MainConfig {

    @Bean(initMethod = "init")
    public OwlMessageProcessor owlMessageProcessor(MeterRegistry meterRegistry,
            MeasurementsUnmarshaller measurementsUnmarshaller, ThreadPoolTaskScheduler threadPoolTaskScheduler,
            @Value("${multicast-address}") String multicastAddress, @Value("${multicast-port}") int multicastPort,
            @Value("${multicast-listener-delay}") long multicastListenerDelay) {
        return new OwlMessageProcessorImpl(meterRegistry, measurementsUnmarshaller, threadPoolTaskScheduler,
                multicastAddress, multicastPort, multicastListenerDelay);
    }

    @Bean
    public MeasurementsUnmarshaller measurementsUnmarshaller() {
        return new MeasurementsUnmarshaller();
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }
}
