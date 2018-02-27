package com.demo.core.mybatis.provider;

import java.util.Iterator;
import java.util.Set;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

public class ChooseFieldsToUpdateProvider extends MapperTemplate {
    public ChooseFieldsToUpdateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String updateByPrimaryKeyWithSelectiveFields(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, this.tableName(entityClass)));
        sql.append(updateSetColumns(entityClass, (String)null));
        sql.append(SqlHelper.wherePKColumns(entityClass));
        return sql.toString();
    }

    public static String updateSetColumns(Class<?> entityClass, String entityName) {
        StringBuilder sql = new StringBuilder();
        sql.append("<set>");
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        Iterator var4 = columnList.iterator();

        while(var4.hasNext()) {
            EntityColumn column = (EntityColumn)var4.next();
            if (!column.isId() && column.isUpdatable()) {
                sql.append("<if test=\"@com.pudding.core.mybatis.helper.OGNL@isColumnToUpdate(fieldsToUpdate,'").append(column.getProperty()).append("') \" > \n");
                sql.append(column.getColumnEqualsHolder(entityName) + ",");
                sql.append("</if>");
            }
        }

        sql.append("</set>");
        return sql.toString();
    }
}
