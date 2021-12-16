package com.lguplus.homeshoppingmoa.searchinfoforwadingservice.kafka.schedule;

import com.lguplus.homeshoppingmoa.searchinfoforwadingservice.kafka.component.CustomConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;

@RequiredArgsConstructor
@Component
public class ConsumerPollingTask {

    private final CustomConsumer consumer;

    @Scheduled(fixedDelay = 1000L) // 1초
    public void polling(){
        consumer.poll(Duration.ofSeconds(3));
    }

    /*
    @Scheduled(fixedDelay = 10000L) // 10초
    public void commit(){
        consumer.commit();
    }
    */

}
