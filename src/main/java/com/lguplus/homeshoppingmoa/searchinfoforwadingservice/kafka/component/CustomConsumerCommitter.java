package com.lguplus.homeshoppingmoa.searchinfoforwadingservice.kafka.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomConsumerCommitter {

    private final KafkaConsumer<String, String> mainProductConsumer;

    public void commit() {
        this.mainProductConsumer.commitSync();
        log.info(">>> Commit!");
    }
}
