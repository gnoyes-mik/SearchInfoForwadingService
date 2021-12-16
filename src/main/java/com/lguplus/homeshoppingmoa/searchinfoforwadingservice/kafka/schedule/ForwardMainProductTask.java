package com.lguplus.homeshoppingmoa.searchinfoforwadingservice.kafka.schedule;

import com.lguplus.homeshoppingmoa.searchinfoforwadingservice.kafka.component.MainProductForwarder;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ForwardMainProductTask {

    private final MainProductForwarder forwarder;

    @Scheduled(fixedDelay = 30000L)
    public void forwardMainProduct() {
        forwarder.forward();
    }
}
