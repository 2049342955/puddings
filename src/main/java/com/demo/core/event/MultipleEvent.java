package com.demo.core.event;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.ApplicationEvent;

public class MultipleEvent<T> extends ApplicationEvent {
    private String eventCode;
    private Map<String, Object> additionalAttributes = new HashMap();

    public MultipleEvent(T source) {
        super(source);
    }

    public MultipleEvent(String eventCode, T source) {
        super(source);
        this.eventCode = eventCode;
    }

    public T getSource() {
        return (T) super.getSource();
    }

    public String getEventCode() {
        return this.eventCode;
    }

    public Map<String, Object> getAdditionalAttributes() {
        return this.additionalAttributes;
    }

    public void addAttribute(String key, Object value) {
        this.additionalAttributes.put(key, value);
    }

    public void addAttributes(Map map) {
        this.additionalAttributes.putAll(map);
    }

    public Object getAttribute(String key) {
        return this.additionalAttributes.get(key);
    }
}
