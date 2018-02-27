package com.demo.core.client;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;

public class BeanUtil {
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public BeanUtil() {
    }

    public static <T> T copyBean(Object source, Class<T> targetClass) {
        try {
            T result = targetClass.newInstance();
            BeanUtils.copyProperties(source, result);
            return result;
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static <T> PageInfo<T> pageMap2pageBean(Map<String, Object> pageInfo, Class<T> tClass) {
        PageInfo<T> result = new PageInfo();
        result.setPageNum((Integer)pageInfo.get("pageNum"));
        result.setPageSize((Integer)pageInfo.get("pageSize"));
        result.setSize((Integer)pageInfo.get("size"));
        result.setOrderBy((String)pageInfo.get("orderBy"));
        result.setStartRow((Integer)pageInfo.get("startRow"));
        result.setEndRow((Integer)pageInfo.get("endRow"));
        result.setTotal(new Long(pageInfo.get("total").toString()));
        result.setPages((Integer)pageInfo.get("pages"));
        result.setFirstPage((Integer)pageInfo.get("firstPage"));
        result.setPrePage((Integer)pageInfo.get("prePage"));
        result.setNextPage((Integer)pageInfo.get("nextPage"));
        result.setLastPage((Integer)pageInfo.get("lastPage"));
        result.setIsFirstPage((Boolean)pageInfo.get("isFirstPage"));
        result.setIsLastPage((Boolean)pageInfo.get("isLastPage"));
        result.setHasPreviousPage((Boolean)pageInfo.get("hasPreviousPage"));
        result.setHasNextPage((Boolean)pageInfo.get("hasNextPage"));
        result.setNavigatePages((Integer)pageInfo.get("navigatePages"));
        List<Integer> navigatepageNums = (List)pageInfo.get("navigatepageNums");
        if (navigatepageNums != null && !navigatepageNums.isEmpty()) {
            int[] p = new int[navigatepageNums.size()];

            for(int i = 0; i < navigatepageNums.size(); ++i) {
                p[i] = (Integer)navigatepageNums.get(i);
            }

            result.setNavigatepageNums(p);
        }

        result.setList(listMap2listBean((List)pageInfo.get("list"), tClass));
        return result;
    }

    public static <T> List<T> listMap2listBean(List<Map<String, Object>> listMap, Class<T> tClass) {
        if (listMap != null && !listMap.isEmpty()) {
            List<T> result = new ArrayList();
            Iterator var3 = listMap.iterator();

            while(var3.hasNext()) {
                Map<String, Object> map = (Map)var3.next();
                result.add(map2Bean(map, tClass));
            }

            return result;
        } else {
            return null;
        }
    }

    public static <T> T map2Bean(Map<String, Object> map, Class<T> targetClass) {
        if (map == null) {
            return null;
        } else {
            return Map.class.isAssignableFrom(targetClass) ? (T) map : JSONObject.parseObject(JSONObject.toJSONString(map), targetClass);
        }
    }

    public static List<Field> getAllField(List<Field> fields, Class clazz) {
        List<Field> newField = Arrays.asList(clazz.getDeclaredFields());
        if (fields == null) {
            fields = new ArrayList();
        }

        ((List)fields).addAll(newField);
        if (clazz.getSuperclass() != null) {
            getAllField((List)fields, clazz.getSuperclass());
        }

        return (List)fields;
    }

    public static <T> Map<String, Object> bean2UrlMap(T obj) {
        if (obj == null) {
            return null;
        } else {
            HashMap map = new HashMap(10);

            try {
                List<Field> fields = getAllField(new ArrayList(), obj.getClass());
                Iterator var3 = fields.iterator();

                while(true) {
                    while(true) {
                        while(true) {
                            Field field;
                            Object o;
                            do {
                                int mod;
                                do {
                                    do {
                                        if (!var3.hasNext()) {
                                            return map;
                                        }

                                        field = (Field)var3.next();
                                        mod = field.getModifiers();
                                    } while(Modifier.isStatic(mod));
                                } while(Modifier.isFinal(mod));

                                field.setAccessible(true);
                                o = field.get(obj);
                            } while(o == null);

                            Class clazz = field.getType();
                            if (!clazz.equals(String.class) && !clazz.equals(Integer.class) && !clazz.equals(Byte.class) && !clazz.equals(Long.class) && !clazz.equals(Double.class) && !clazz.equals(Float.class) && !clazz.equals(Character.class) && !clazz.equals(Short.class) && !clazz.equals(BigDecimal.class) && !clazz.equals(BigInteger.class) && !clazz.equals(Boolean.class) && !clazz.isPrimitive()) {
                                if (clazz.equals(Date.class)) {
                                    map.put(field.getName(), ((Date)o).getTime());
                                } else {
                                    Map<String, Object> temp = bean2UrlMap(o);
                                    Iterator iter = temp.keySet().iterator();

                                    while(iter.hasNext()) {
                                        String key = (String)iter.next();
                                        map.put(field.getName() + "." + key, temp.get(key));
                                    }
                                }
                            } else {
                                map.put(field.getName(), o);
                            }
                        }
                    }
                }
            } catch (IllegalAccessException var11) {
                var11.printStackTrace();
                return map;
            }
        }
    }
}
