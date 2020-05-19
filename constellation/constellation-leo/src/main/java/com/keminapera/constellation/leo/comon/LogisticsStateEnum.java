package com.keminapera.constellation.leo.comon;

import lombok.Getter;
/**
 * 本系统物流状态枚举类
 *
 * @author KeminaPera
 * @date 2019/10/2 8:09
 */
public enum LogisticsStateEnum {
    /**
     * 未知状态
     */
    UNKNOW(0, "疑难状态"),
    /**
     * 已邮出
     */
    ACCEPTED(1, "已邮出"),
    /**
     * 运输中
     */
    TRANSITING(2, "运输中"),
    /**
     * 派送中
     */
    DELIVERING(3, "派送中"),
    /**
     * 被拒签
     */
    REFUSED(4, "被拒签"),
    /**
     * 已完成
     */
    FINISHED(5, "已签收"),
    /**
     * 退回状态
     */
    BACKING(6, "回退中");
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
