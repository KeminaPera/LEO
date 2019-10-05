package com.keminapera.constellation.leo.service.manager;

import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;

import java.util.List;

/**
 * 物流信息提取器接口
 *
 * @author KeminaPera
 * @date 2019/10/4 18:04
 */
public interface ILogisticsInfoExtractor {
    /**
     * 从第三方返回的数据解析出物流信息
     *
     * @param data 第三方返回数据
     * @return 物流信息包装类
     */
    LogisticsVo doExtractorLogistics(String data);

    /**
     * 从第三方返回数据中解析出物流信息（物流单号本系统已存在）
     *
     * @param data                 第三方返回数据
     * @param logisticsInfoExisted 该物流信息在本地是否已经存在
     * @return 物流信息列表
     */
    List<LogisticsInfo> doExtractorLogisticsInfoList(String data, boolean logisticsInfoExisted);
}
