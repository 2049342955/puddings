package com.demo.core.event.handle;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.demo.core.event.IEventHandler;
import com.demo.core.event.MultipleEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

public class MutipleEventHandler implements ApplicationListener<MultipleEvent>, ApplicationContextAware {
    private static Logger logger = LoggerFactory.getLogger(MutipleEventHandler.class);
    private ApplicationContext applicationContext;
    private Map<String, IEventHandler> handlerMap;

    public MutipleEventHandler() {
    }

    public void onApplicationEvent(MultipleEvent event) {
        if (this.handlerMap == null) {
            this.initHandlerMap();
        }

        Collection<IEventHandler> handlers = this.handlerMap.values();
        Iterator var3 = handlers.iterator();

        while(var3.hasNext()) {
            IEventHandler handler = (IEventHandler)var3.next();
            if (handler.supportEvent(event.getEventCode())) {
                handler.onEvent(event);
            }
        }

    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private void initHandlerMap() {
        this.handlerMap = new HashMap(20);
        String[] handlerNames = this.applicationContext.getBeanNamesForType(IEventHandler.class);

        for(int i = 0; i < handlerNames.length; ++i) {
            try {
                IEventHandler eventHandler = (IEventHandler)this.applicationContext.getBean(handlerNames[i]);
                this.handlerMap.put(handlerNames[i], eventHandler);
            } catch (Exception var4) {
                logger.error("could not find bean names with : {}", handlerNames[i]);
            }
        }

    }
}
