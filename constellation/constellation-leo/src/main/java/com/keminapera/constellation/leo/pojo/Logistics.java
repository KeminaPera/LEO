package com.keminapera.constellation.leo.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 物流实体类
 *
 * @author KeminaPera
 * @date 2019/10/2 6:25
 */
@Data
public class Logistics {
    /**
     * 快递单号（采用快递公司的标准）
     */
    private String number;
    /**
     * 对应的快递公司
     */
    private int company;
    /**
     * 收件人
     */
    private String addressee;
    /**
     * 收件人电话
     */
    private String addresseePhone;
    /**
     * 收件人地址
     */
    private String addresseeAddress;
    /**
     * 发货时间
     */
    private Date sendTime;
    /**
     * 最新进展
     */
    private String latestProgress;
    /**
     * 最新进展对应时间
     */
    private Date latestTime;
    /**
     * 物流状态
     */
    private Integer state;
}
