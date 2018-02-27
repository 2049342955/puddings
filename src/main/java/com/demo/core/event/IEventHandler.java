package com.demo.core.event;

public interface IEventHandler<T> {
    boolean supportEvent(String var1);

    void onEvent(MultipleEvent<T> var1);
}
