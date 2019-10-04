package com.keminapera.constellation.leo.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 物流信息实体类
 *
 * @author KeminaPera
 * @date 2019/10/2 6:25
 */
@Data
public class LogisticsInfo {
    /**
     * 编号（32位uuid）
     */
    private String id;
    /**
     * 物流信息--时间
     */
    private Date time;
    /**
     * 物流信息--对应描述
     */
    private String desc;
    /**
     * 快递单号{@link Logistics}的number属性
     */
    private String numberLogistics;
}
