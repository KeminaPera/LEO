package com.keminapera.constellation.leo.pojo;

import lombok.Data;

/**
 * 快递公司实体类
 *
 * @author KeminaPera
 * @date 2019/10/2 6:25
 */
@Data
public class Company {
    /**
     * 编号（32位uuid）
     */
    private String id;
    /**
     * 中文全名
     */
    private String fullname;
    /**
     * 中文简称
     */
    private String shortname;
    /**
     * 英文简称（唯一）
     */
    private String com;
    /**
     * 公司查询快递的url
     */
    private String url;
    /**
     * 公司编号（唯一， int型数据）
     */
    private Integer number;
    /**
     * 是否有效
     */
    private Integer valid;
}
