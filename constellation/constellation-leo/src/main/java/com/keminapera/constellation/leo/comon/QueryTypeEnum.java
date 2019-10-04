package com.keminapera.constellation.leo.comon;

import lombok.Getter;

/**
 * 是否有效枚举类
 *
 * @author KeminaPera
 * @date 2019/10/2 8:09
 */
@Getter
public enum QueryTypeEnum {
    /**
     * 有效
     */
    VALID(1, "有效的"),
    /**
     * 无效
     */
    INVALID(2, "无效的"),
    /**
     * 全部（有效和无效）
     */
    ALL(3, "全部");
    /**
     * 类型
     */
    private int type;
    /**
     * 该类型的描述
     */
    private String desc;

    /**
     * 获取该类型的说明
     * @param currentType 当前类型
     * @return 该类型的描述
     */
    public String getDesc(int currentType) {
        for (QueryTypeEnum queryTypeEnum : values()) {
            if (queryTypeEnum.getType() == currentType) {
                return queryTypeEnum.getDesc();
            }
        }
        return CommonProperties.NOT_KNOW_STATE;
    }
    QueryTypeEnum(int type, String desc){
        this.type = type;
        this.desc = desc;
    }
}
