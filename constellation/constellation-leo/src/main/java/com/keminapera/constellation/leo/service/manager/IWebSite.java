package com.keminapera.constellation.leo.service.manager;

import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;

import java.util.List;

/**
 * 综合查询物流信息的网站
 *
 * @author KeminaPera
 * @date 2019/10/4 22:06
 */
public interface IWebSite extends IExpressCompany {
    /**
     * 根据快递单号和公司查询物流信息
     *
     * @param number  快递单号
     * @param company 公司
     * @return 物流信息
     */
    List<LogisticsInfo> queryLogisticsInfoList(String number, int company);

    /**
     * 根据快递单号和公司查询物流信息
     *
     * @param number  快递单号
     * @param company 快递公司
     * @return 物流信息
     */
    LogisticsVo queryLogistics(String number, int company);
}
