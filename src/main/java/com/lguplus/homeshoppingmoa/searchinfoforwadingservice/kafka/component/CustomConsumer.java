package com.lguplus.homeshoppingmoa.searchinfoforwadingservice.kafka.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomConsumer {

    private final MainProductForwarder forwarder;
    private final KafkaConsumer<String, String> mainProductConsumer;
    // private final List<String> topic = Collections.singletonList("test");


    public void poll(Duration timeOut) {
        ConsumerRecords<String, String> poll = this.mainProductConsumer.poll(timeOut);
        for (ConsumerRecord<String, String> record : poll) {
            printRecord(record);
            forwarder.addLast(record.value());
        }
        printOffsets(this.mainProductConsumer);
    }

    private void printOffsets(KafkaConsumer<String, String> consumer) {
        final TopicPartition topicPartition = new TopicPartition("topic-sample", 0); // TODO 설정으로 변경
        Map<TopicPartition, OffsetAndMetadata> committed = consumer.committed(new HashSet<>(Collections.singletonList(topicPartition)));
        OffsetAndMetadata offsetAndMetadata = committed.get(topicPartition);
        long position = consumer.position(topicPartition);
        log.info(">>> Offset info] Committed: {}, current position: {}", offsetAndMetadata == null ? null : offsetAndMetadata.offset(), position);
    }

    private void printRecord(ConsumerRecord<String, String> record) {
        System.out.print("==> [Record]");
        StringBuilder sb = new StringBuilder();
        sb.append(" topic:").append(record.topic());
        sb.append(" partition:").append(record.partition());
        sb.append(" offset:").append(record.offset());
        sb.append(" key:").append(record.key());
        sb.append(" value:").append(record.value());

        System.out.println(sb);
    }

}
