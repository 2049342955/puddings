package com.demo.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtil {
    private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    public BeanUtil() {
    }

    public static Map<String, Object> bean2Map(Object bean) {
        Map<String, Object> map = new HashMap(10);
        if (bean == null) {
            return map;
        } else {
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                PropertyDescriptor[] var4 = propertyDescriptors;
                int var5 = propertyDescriptors.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    PropertyDescriptor property = var4[var6];
                    String key = property.getName();
                    if (!"class".equals(key)) {
                        Method getter = property.getReadMethod();
                        Object value = getter.invoke(bean);
                        if (value != null) {
                            map.put(key, value);
                        }
                    }
                }
            } catch (Exception var11) {
                logger.error("bean2Map Error " + var11);
            }

            return map;
        }
    }
}
