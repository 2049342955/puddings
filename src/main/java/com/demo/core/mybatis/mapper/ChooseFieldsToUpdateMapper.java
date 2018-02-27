package com.demo.core.mybatis.mapper;

import com.demo.core.mybatis.provider.ChooseFieldsToUpdateProvider;
import org.apache.ibatis.annotations.UpdateProvider;

public interface ChooseFieldsToUpdateMapper<T> {
    @UpdateProvider(
            type = ChooseFieldsToUpdateProvider.class,
            method = "dynamicSQL"
    )
    int updateByPrimaryKeyWithSelectiveFields(T var1);
}
