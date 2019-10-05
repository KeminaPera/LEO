package com.keminapera.constellation.leo.comon;

import lombok.Getter;

/**
 * 快递公司枚举类
 *
 * @author KeminaPera
 * @date 2019/10/2 8:09
 */
@Getter
public enum CompanyEnum {
    /**
     * 韵达快递
     */
    YUNDA(1, "韵达快递"),
    /**
     * 圆通快递
     */
    YUANTONG(2, "圆通快递"),
    /**
     * 申通快递
     */
    SHENTONG(3, "申通快递"),
    /**
     * 中通快递
     */
    ZHONGTONG(4, "中通快递"),
    /**
     * 天天快递
     */
    TIANTIAN(5, "天天快递");
    /**
     * 快递公司号（唯一）
     */
    private int companyNumber;
    /**
     * 快递公司名称
     */
    private String companyName;

    CompanyEnum(int companyNumber, String companyName) {
        this.companyNumber = companyNumber;
        this.companyName = companyName;
    }

    /**
     * 通过number获取名称
     *
     * @param number 序号
     * @return 快递公司名称
     */
    public static String getCompanyName(int number) {
        for (CompanyEnum companyEnum : values()) {
            if (companyEnum.getCompanyNumber() == number) {
                return companyEnum.getCompanyName();
            }
        }
        return CommonProperties.NOT_KNOW_STATE;
    }
}
