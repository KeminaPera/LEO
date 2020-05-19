package com.keminapera.constellation.leo.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求参数的构造器
 *
 * @author KeminaPera
 * @date 2019/10/3 8:28
 */
public final class RequestParamBuilderUtil {
    /**
     * 请求参数构造方法
     *
     * @param name  key
     * @param value value
     * @return key-value的Map
     */
    public static Map<String, String> builder(String name, String value) {
        Map<String, String> requestParam = new HashMap<>(16);
        requestParam.put(name, value);
        return requestParam;
    }

    /**
     * 请求参数构造方法
     *
     * @param key1   key1
     * @param value1 value1
     * @param key2   key2
     * @param value2 value2
     * @return key-value
     */
    public static Map<String, String> builder(String key1, String value1, String key2, String value2) {
        Map<String, String> requestParam = new HashMap<>(16);
        requestParam.put(key1, value1);
        requestParam.put(key2, value2);
        return requestParam;
    }

    /**
     * 请求参数构造方法
     * 推荐用法：
     *
     * @param keyList   key列表
     * @param valueList value列表
     * @return key-value的Map
     */
    public static Map<String, String> builder(List<String> keyList, List<String> valueList) {
        if (CollectionUtils.isNotEmpty(keyList) && CollectionUtils.isNotEmpty(valueList)) {
            if (keyList.size() != valueList.size()) {
                throw new IllegalArgumentException("请求参数数量不一致【kay的数量】" + keyList.size() + "【value的数量】" + valueList.size());
            }
        }
        Map<String, String> requestParam = new HashMap<>(16);
        for (int i = 0; i < keyList.size(); i++) {
            requestParam.put(keyList.get(i), valueList.get(i));
        }
        return requestParam;
    }
}
