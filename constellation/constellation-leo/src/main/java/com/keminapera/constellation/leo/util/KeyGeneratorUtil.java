package com.keminapera.constellation.leo.util;

import java.util.UUID;

/**
 * 主键生成器
 *
 * @author KeminaPera
 * @date 2019/10/3 9:36
 */
public final class KeyGeneratorUtil {
    /**
     * 字符串类型主键生成器
     * @return 32位字符
     */
    public static String genetateStringKey(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
