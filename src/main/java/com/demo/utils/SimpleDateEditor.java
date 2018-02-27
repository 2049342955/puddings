package com.demo.utils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class SimpleDateEditor extends PropertyEditorSupport {
    private static final Logger logger = LoggerFactory.getLogger(SimpleDateEditor.class);
    public static final String NUMBER_PATTERN = "^[1-9]\\d*$";
    public static final String YMD_PATTERN = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
    public static final String YMD_FORMAT = "yyyy-MM-dd";
    public static final String YMDHMS_PATTERN = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
    public static final String YMDHMS_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD_PATTERN2 = "[0-9]{4}/[0-9]{2}/[0-9]{2}";
    public static final String YMD_FORMAT2 = "yyyy/MM/dd";
    public static final String YMDHMS_PATTERN2 = "[0-9]{4}/[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
    public static final String YMDHMS_FORMAT2 = "yyyy/MM/dd HH:mm:ss";
    public static final String GMT_PATTERN = "[a-zA-Z]{3} [a-zA-Z]{3} [0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2} GMT[+-]{1}[0-9]{2}:[0-9]{2} [0-9]{4}";
    public static final String GMT_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
    public static final String CST_PATTERN = "[a-zA-Z]{3} [a-zA-Z]{3} [0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2} CST [0-9]{4}";
    public static final String CST_FORMAT = "EEE MMM dd HH:mm:ss Z yyyy";

    public SimpleDateEditor() {
    }

    public String getAsText() {
        Date value = (Date)this.getValue();
        return value.getTime() + "";
    }

    public void setAsText(String text) throws IllegalArgumentException {
        logger.debug("text  is {}", text);
        if (StringUtils.isEmpty(text)) {
            logger.debug("进入分支 :{}", "isEmpty");
            this.setValue((Object)null);
        } else if (Pattern.compile("^[1-9]\\d*$").matcher(text).find()) {
            Long value = Long.parseLong(text);
            logger.debug("进入分支 :{} long value is {}", "^[1-9]\\d*$", value);
            this.setValue(new Date(value));
        } else {
            Date value;
            SimpleDateFormat simpleDateFormat;
            if (Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}").matcher(text).find()) {
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try {
                    value = simpleDateFormat.parse(text);
                    logger.debug("进入分支 :{} Date value is {}", "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}", value);
                    this.setValue(value);
                } catch (ParseException var9) {
                    var9.printStackTrace();
                }
            } else if (Pattern.compile("[0-9]{4}/[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}").matcher(text).find()) {
                simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                try {
                    value = simpleDateFormat.parse(text);
                    logger.debug("进入分支 :{} Date value is {}", "[0-9]{4}/[0-9]{2}/[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}", value);
                    this.setValue(value);
                } catch (ParseException var8) {
                    var8.printStackTrace();
                }
            } else if (Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}").matcher(text).find()) {
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    value = simpleDateFormat.parse(text);
                    logger.debug("进入分支 :{} Date value is {}", "[0-9]{4}-[0-9]{2}-[0-9]{2}", value);
                    this.setValue(value);
                } catch (ParseException var7) {
                    var7.printStackTrace();
                }
            } else if (Pattern.compile("[0-9]{4}/[0-9]{2}/[0-9]{2}").matcher(text).find()) {
                simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

                try {
                    value = simpleDateFormat.parse(text);
                    logger.debug("进入分支 :{} Date value is {}", "yyyy/MM/dd", value);
                    this.setValue(value);
                } catch (ParseException var6) {
                    var6.printStackTrace();
                }
            } else if (Pattern.compile("[a-zA-Z]{3} [a-zA-Z]{3} [0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2} GMT[+-]{1}[0-9]{2}:[0-9]{2} [0-9]{4}").matcher(text).find()) {
                simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

                try {
                    value = simpleDateFormat.parse(text);
                    logger.debug("进入分支 :{} Date value is {}", "[a-zA-Z]{3} [a-zA-Z]{3} [0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2} GMT[+-]{1}[0-9]{2}:[0-9]{2} [0-9]{4}", value);
                    this.setValue(value);
                } catch (ParseException var5) {
                    var5.printStackTrace();
                }
            } else if (Pattern.compile("[a-zA-Z]{3} [a-zA-Z]{3} [0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2} CST [0-9]{4}").matcher(text).find()) {
                simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");

                try {
                    value = simpleDateFormat.parse(text);
                    logger.debug("进入分支 :{} Date value is {}", "EEE MMM dd HH:mm:ss Z yyyy", value);
                    this.setValue(value);
                } catch (ParseException var4) {
                    var4.printStackTrace();
                }
            } else {
                logger.debug("进入分支 :{}  text is {}", "else", text);
                this.setValue(text);
            }
        }

    }
}
