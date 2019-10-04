package com.keminapera.leo;

import java.util.UUID;

/**
 * 主键生成器
 *
 * @author KeminaPera
 * @date 2019/10/3 5:55
 */
public class Generator {
    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString().replaceAll("-", "");
        System.out.println(s);
    }
}
