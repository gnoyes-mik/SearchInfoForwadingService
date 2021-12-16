package com.lguplus.homeshoppingmoa.searchinfoforwadingservice.kafka.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

@Slf4j
@Component
@RequiredArgsConstructor
public class MainProductForwarder {

    private final CustomConsumerCommitter customConsumerCommitter;
    private static final Deque<String> forwardDeque = new ArrayDeque<>();
    private static final ArrayBlockingQueue<String> f = new ArrayBlockingQueue<>(100_000);

    private final Object lock = new Object();

    public boolean addFirst(String item) {
        return forwardDeque.offerFirst(item);
    }

    public boolean addLast(String item) {
        return forwardDeque.offerLast(item);
    }

    public String getFirst() {
        try {
            return forwardDeque.removeFirst();
        } catch (NoSuchElementException nsee) {
            log.error("> Failed to get first from forwardDeque! msg : {}", nsee.getMessage());
            throw new NoSuchElementException();
        }
    }

    public String getLast() {
        try {
            return forwardDeque.removeLast();
        } catch (NoSuchElementException nsee) {
            log.error("> Failed to get last from forwardDeque! msg : {}", nsee.getMessage());
            throw new NoSuchElementException();
        }
    }

    public void forward() {
        try {
            List<String> payload = new ArrayList<>();
            while (forwardDeque.iterator().hasNext()) {
                String next = this.getFirst();
                payload.add(next);
            }
            System.out.println(">>> 검색서버로 보낼 payload = " + payload);
            customConsumerCommitter.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
