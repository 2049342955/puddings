package com.demo.core.message;

import java.text.MessageFormat;
import java.util.Locale;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.context.support.AbstractMessageSource;

public class CacheMessageSource extends AbstractMessageSource {
    private static final String EMPTY_STRING = "";
    private Cache cache;

    public CacheMessageSource(Cache cache) {
        this.cache = cache;
    }

    protected MessageFormat resolveCode(String code, Locale locale) {
        ValueWrapper value = this.cache.get(code + "_" + locale.toString());
        return value != null && value.get() != null ? this.createMessageFormat(value.get().toString(), locale) : this.createMessageFormat("", locale);
    }
}
