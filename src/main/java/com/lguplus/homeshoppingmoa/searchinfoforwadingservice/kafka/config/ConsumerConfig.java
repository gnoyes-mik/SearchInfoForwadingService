package com.lguplus.homeshoppingmoa.searchinfoforwadingservice.kafka.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Properties;

@Configuration
public class ConsumerConfig {

    private static final String BROKERS = "192.168.0.170:9092"; // 브로커 리스트
    private final TopicPartition topicPartition = new TopicPartition("topic-sample", 0); // TODO 설정으로 변경

    @Bean
    public KafkaConsumer<String, String> mainProductConsumer() {
        KafkaConsumer<String, String> mainProductConsumer = new KafkaConsumer<>(this.kafkaConsumerProperties());
        mainProductConsumer.assign(Collections.singletonList(topicPartition));
        return mainProductConsumer;
    }

    @Bean
    public Properties kafkaConsumerProperties() {
        Properties prop = new Properties();

        // Broker list 정의
        prop.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKERS);
        // Key, Value에 사용될 시리얼라이져 지정
        prop.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        prop.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, "c-group-test");          // 컨슈머 그룹 아이디 지정
        prop.put(org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");     // auto-commit 설정
        prop.put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // earliest / latest

        return prop;
    }
}
