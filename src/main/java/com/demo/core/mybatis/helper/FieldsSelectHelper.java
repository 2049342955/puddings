package com.demo.core.mybatis.helper;

import java.util.HashSet;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class FieldsSelectHelper {
    private static ThreadLocal<Set<String>> SELECTD_FIELDS = new ThreadLocal();

    public FieldsSelectHelper() {
    }

    public static Set<String> getSelectedFields() {
        return (Set)SELECTD_FIELDS.get();
    }

    public static void setSelectedFields(Set<String> selectedFields) {
        SELECTD_FIELDS.set(selectedFields);
    }

    public static void clearSelectedFields() {
        SELECTD_FIELDS.remove();
    }

    public static void setSelectedFields(String... selectedFields) {
        Set<String> set = new HashSet();
        set.addAll(CollectionUtils.arrayToList(selectedFields));
        SELECTD_FIELDS.set(set);
    }
}
