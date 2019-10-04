package com.keminapera.constellation.leo.service.manager;

import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;

import java.util.List;

/**
 * 快递100网址接口类
 *
 * @author KeminaPera
 * @date 2019/10/2 8:47
 */
public interface IKuaiDi100<T> extends IExpressCompany {
    /**
     * 根据快递单号和公司查询物流信息
     * @param number 快递单号
     * @param company 公司
     * @return 物流信息
     */
    List<LogisticsInfo> queryLogisticdInfo(String number, int company);

    /**
     * 根据快递单号和公司查询物流信息
     * @param number 快递单号
     * @param company 快递公司
     * @return 物流信息
     */
    LogisticsVo queryLogisticdInfoNothing(String number, int company);
}
