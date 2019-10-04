package com.keminapera.constellation.leo.service.manager;

import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;

import java.util.List;

/**
 * 快递信息查询父接口
 *
 * @author KeminaPera
 * @date 2019/10/2 8:47
 */
public interface IExpressCompany {
    /**
     * 根据快递单号查询
     * @param number 快递单号
     * @return 物流信息
     */
    LogisticsVo queryLogistics(String number);

    /**
     * 查询该快递单号的物流信息（本系统已经有该单号的记录）
     * @param number 快递单号
     * @return 物流信息列表
     */
    List<LogisticsInfo> queryLogisticsInfoList(String number);
}
