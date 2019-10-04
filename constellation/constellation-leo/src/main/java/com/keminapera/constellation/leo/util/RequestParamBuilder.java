package com.keminapera.constellation.leo.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求参数的构造器
 *
 * @author KeminaPera
 * @date 2019/10/3 8:28
 */
public final class RequestParamBuilder {
    public static Map<String, String> builder(String name, String value) {
        Map<String, String> requestParam = new HashMap<>(16);
        requestParam.put(name, value);
        return requestParam;
    }
}
