package com.demo.core.mybatis.mapper;

import tk.mybatis.mapper.common.Mapper;

public interface CommonMapper<T> extends Mapper<T>, ChooseFieldsToUpdateMapper<T> {
}
