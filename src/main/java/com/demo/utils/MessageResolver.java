package com.demo.utils;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

public class MessageResolver {
    private MessageSource messageSource;
    private Locale locale;

    public MessageResolver(MessageSource messageSource, Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    public String getMessage(String messageCode, Object[] args) {
        if (!this.match(messageCode)) {
            return messageCode;
        } else {
            if (args != null) {
                for(int i = 0; i < args.length; ++i) {
                    String arg = args[i].toString();
                    if (this.match(arg.toString())) {
                        arg = this.messageSource.getMessage(arg, new Object[0], this.locale);
                        args[i] = arg;
                    }
                }
            }

            String msg = this.messageSource.getMessage(messageCode, args, this.locale);
            return StringUtils.isEmpty(msg) ? messageCode : msg;
        }
    }

    private boolean match(String messageCode) {
        return messageCode.startsWith("msg.") || messageCode.startsWith("label") || messageCode.startsWith("btn");
    }
}
