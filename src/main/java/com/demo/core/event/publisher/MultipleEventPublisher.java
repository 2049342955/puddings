package com.demo.core.event.publisher;

import com.demo.core.event.MultipleEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

public class MultipleEventPublisher implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;

    public MultipleEventPublisher() {
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void publish(String eventCode, Object source) {
        MultipleEvent event = new MultipleEvent(eventCode, source);
        this.publisher.publishEvent(event);
    }
}
