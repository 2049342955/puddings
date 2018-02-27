package com.demo.core.mybatis.helper;

import java.util.Set;

public class OGNL {
    public OGNL() {
    }

    public static boolean isColumnToUpdate(Set<String> fieldsToUpdate, String propertyName) {
        Set<String> fields = FieldsSelectHelper.getSelectedFields();
        if (fieldsToUpdate != null && fieldsToUpdate.contains(propertyName)) {
            return true;
        } else {
            return fields != null ? fields.contains(propertyName) : false;
        }
    }
}
