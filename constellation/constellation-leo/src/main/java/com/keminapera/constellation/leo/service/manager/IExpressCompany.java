package com.keminapera.constellation.leo.service.manager;

import com.keminapera.constellation.leo.entity.LogisticsVo;
import org.jetbrains.annotations.NotNull;

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
    LogisticsVo queryLogistics(@NotNull String number);

}
