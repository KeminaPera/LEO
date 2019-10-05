package com.keminapera.constellation.leo.service.manager.kusaidi100;

import com.keminapera.constellation.leo.comon.CommonProperties;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 快递公司枚举类
 *
 * @author KeminaPera
 * @date 2019/10/2 8:09
 */
@Getter
public enum ExpressCompanyEnum {
    /**
     * 韵达快递
     */
    YUNDA(1, "yunda"),
    /**
     * 圆通快递
     */
    YUANTONG(2, "yuantong"),
    /**
     * 申通快递
     */
    SHENTONG(3, "shentong"),
    /**
     * 中通快递
     */
    ZHONGTONG(4, "zhongtong"),
    /**
     * 天天快递
     */
    TIANTIAN(5, "tiantian");
    /**
     * 快递公司序号（唯一）
     */
    private int number;
    /**
     * 对应的英文缩写
     */
    private String com;

    /**
     * 通过number获取com
     * @param number 序号
     * @return com
     */
    public static String getCom(int number) {
        for (ExpressCompanyEnum expressCompanyEnum : values()) {
            if (expressCompanyEnum.getNumber() == number) {
                return expressCompanyEnum.getCom();
            }
        }
        return CommonProperties.NOT_KNOW_STATE;
    }

    /**
     * 根据com获取number
     * @param com com
     * @return number
     */
    public static int getNumber(String com) {
        for (ExpressCompanyEnum expressCompanyEnum : values()) {
            if (StringUtils.equals(com, expressCompanyEnum.getCom())) {
                return expressCompanyEnum.getNumber();
            }
        }
        return -1;
    }
    ExpressCompanyEnum(int number, String com) {
        this.number = number;
        this.com = com;
    }
}
