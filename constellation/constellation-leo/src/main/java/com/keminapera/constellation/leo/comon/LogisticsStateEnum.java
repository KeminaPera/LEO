package com.keminapera.constellation.leo.comon;

import lombok.Getter;
/**
 * 物流状态枚举类
 *
 * @author KeminaPera
 * @date 2019/10/2 8:09
 */
public enum LogisticsStateEnum {
    /**
     * 已下单
     */
    ODERED(1, "已下单"),
    /**
     * 仓库处理中
     */
    PROCESSING(2, "仓库处理中"),
    /**
     * 运输中
     */
    TRANSITING(3, "运输中"),
    /**
     * 派送中
     */
    DELIVERING(4, "派送中"),
    /**
     * 已完成
     */
    FINISHED(5, "已完成");
    /**
     * 物流状态
     */
    @Getter
    private int state;
    /**
     * 对应的描述
     */
    @Getter
    private String desc;

    public static String getDesc(int state) {
        for (LogisticsStateEnum currentLogistics : LogisticsStateEnum.values()) {
            if (currentLogistics.getState() == state) {
                return currentLogistics.getDesc();
            }
        }
        return CommonProperties.NOT_KNOW_STATE;
    }

    LogisticsStateEnum(int state, String desc) {
        this.state = state;
        this.desc = desc;
    }
}
